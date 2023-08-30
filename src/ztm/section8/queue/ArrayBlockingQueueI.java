package ztm.section8.queue;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

public class ArrayBlockingQueueI<E> implements BlockingQueueI<E> {

    final Object[] items;

    int takeIndex; // items index for next take, poll, peek or remove
    int putIndex; // items index for next put, offer, or add
    int count;
    final ReentrantLock lock; // Main lock guarding all access

    /*
    A Lock replaces the use of synchronized methods and statements, a Condition replaces the use of the Object
    monitor methods  (wait, notify and notifyAll)

    Conditions (also known as condition queues or condition variables) provide a means for one thread to suspend
    execution (to "wait") until notified by another thread that some state condition may now be true.
     */

    private final Condition notEmpty;
    private final Condition notFull;

    public ArrayBlockingQueueI(int capacity, boolean fair) {
        if (capacity <= 0)
            throw new IllegalArgumentException();
        this.items = new Object[capacity];
        lock = new ReentrantLock(fair);
        notEmpty = lock.newCondition();
        notFull =  lock.newCondition();
    }

    public ArrayBlockingQueueI(int capacity) {
        this(capacity, false);
    }

    private void enqueue(E e) {
        final Object[] items = this.items;
        items[putIndex] = e;
        if (++putIndex == items.length)
            putIndex = 0;
        count++;
        notEmpty.signal();
    }

    public boolean offer(E e) {
        Objects.requireNonNull(e);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count == items.length)
                return false;
            else {
                enqueue(e);
                return true;
            }
        } finally {
            lock.unlock();
        }
    }

    /*
    Inserts the specified element at the tail of this queue, waiting for space to become available if the
    queue is full.
     */
    public void put(E e) throws InterruptedException {
        Objects.requireNonNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();
            enqueue(e);
        } finally {
            lock.unlock();
        }
    }

    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        Objects.requireNonNull(e);
        ReentrantLock lock = this.lock;
        long nanos = unit.toNanos(timeout);
        lock.lockInterruptibly();
        try {
            while (count == items.length) {
                if (nanos <= 0L)
                    return false;
                nanos = notFull.awaitNanos(nanos);
            }
            enqueue(e);
            return true;
        } finally {
            lock.unlock();
        }
    }

    // Removing elements
    /*
    If you try and lock in the try block, you may end up in a "double trouble" situation, where you throw one
    exception in try and another in finally, since the lock will have failed to lock in the first place...

    This essentially means that you must also .lock() normally in front of try blocks for this reason:

    If acquisition of lock is a problem then code block will stop executing as soon as an exception is encountered.
    */
    private E dequeue() {
        final Object[] items = this.items;
        E item = (E) items[takeIndex];
        items[takeIndex] = null;
        //takeIndex++;
        if (++takeIndex == items.length) takeIndex = 0;
        count--;
        notFull.signal();
        return item;

    }

    public E poll() {
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return count == 0 ? null : dequeue();
        } finally {
            lock.unlock();
        }
    }

    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        ReentrantLock lock = this.lock;
        long nanos = unit.toNanos(timeout);
        lock.lockInterruptibly();
        try {
            while (count == 0) {
                if (nanos <= 0L) {
                    return null;
                }
                nanos = notEmpty.awaitNanos(nanos);
            }
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    // To examine
    public E peek() {
        ReentrantLock lock = this.lock;
        lock.lock();;
        try {
            return (E) items[takeIndex];
        } finally {
            lock.unlock();
        }
    }


    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (count > 0) {
                final Object[] items = this.items;
                for(int i = takeIndex; i< putIndex; i++) {
                    action.accept((E) items[i]);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public IteratorI<E> iterator() {
        return new Itr();
    }

    class Itr implements IteratorI<E>{
        final int takeIndex;

        int count;

        public Itr() {
            takeIndex = ArrayBlockingQueueI.this.takeIndex;
            count = 0;
        }

        @Override
        public boolean hasNext() {
            return this.count != items.length;
        }

        @Override
        public E next() {
            if (hasNext()) {
                return (E) items[(takeIndex + count++) % items.length];
            }
            return null;
        }
    }

    public static void main(String[] args) {
        ArrayBlockingQueueI<Integer> queue = new ArrayBlockingQueueI(5);
        queue.offer(10);
        queue.offer(20);
        queue.offer(30);
        queue.offer(40);
        queue.offer(50);

        queue.poll();

        IteratorI<Integer> iteratorI = queue.iterator();
        while (iteratorI.hasNext())
           System.out.println(iteratorI.next());
    }


}
