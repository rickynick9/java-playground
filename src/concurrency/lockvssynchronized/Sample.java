package concurrency.lockvssynchronized;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/*
Reentrant locks are useful when we have recursive call or callback when we wanted to
acquire the lock on same resource again.

Before Java 5.0, the only mechanisms for coordinating access to shared data were synchronized and volatile.
Unlike intrinsic locking, Lock offers a choice of unconditional, polled, timed, and interruptible lock acquisition,
and all lock and unlock operations are explicit.

(Memory visibility is covered in Section 3.1 and in Chapter 16.)

ReentrantLock supports all of the lock-acquisition modes defined by Lock, providing more flexibility for dealing with
lock unavailability than does synchronized.

public interface Lock {

    void lock();

    void lockInterruptibly() throws InterruptedException;

    Acquires the lock only if it is free at the time of invocation.
    Acquires the lock if it is available and returns immediately with the value true. If the lock is not available
    then this method will return immediately with the value false.

    boolean tryLock();

    Acquires the lock if it is free within the given waiting time and the current thread has not been interrupted.

    boolean tryLock(long time, TimeUnit unit) throws InterruptedException;

    Release the lock
    void unlock();

    Returns a new Condition instance that is bound to this Lock instance.
    Condition newCondition();

    Why create a new locking mechanism that is so similar to intrinsic locking?

    1.Supports timeout : No thread blocking
    A thread gets blocked if it can’t get access to the synchronized block.
    The Lock API provides tryLock() method.
    The thread acquires a lock only if it’s available and not held by any other thread.
    This reduces the blocking time of the thread waiting for the lock.

    Intrinsic locking works fine in most situations but has some functional limitations—
    it is not possible to interrupt a thread waiting to acquire a lock, or to attempt to acquire a lock without being
    willing to wait for it forever.

    2.Lock can be held across methods :
        private ReentrantLock lock;

        public void foo() {
            ...
            lock.lock();
             ...
        }

        public void bar() {
            ...
            lock.unlock();
            ...
        }

     3. supports lock polling
     allows threads to reenter into a lock on a resource (multiple times) without a deadlock situation.
     A thread entering into the lock increases the hold count by one every time.
     Similarly, the hold count decreases when unlock is requested.
     Therefore, a resource is locked until the counter returns to zero.

        ReentrantLock reentrantLock = new ReentrantLock();
        try {
            reentrantLock.lock();
            assertEquals(1, reentrantLock.getHoldCount());
            assertEquals(true, reentrantLock.isLocked());
        } finally {
            reentrantLock.unlock();
            assertEquals(0, reentrantLock.getHoldCount());
            assertEquals(false, reentrantLock.isLocked());
        }

        The lock method increases the hold count by one and locks the resource.
        Similarly, the unlock method decreases the hold count and unlocks a resource if the hold count is zero.

        reentrantLock.lock();
        reentrantLock.lock();
        assertEquals(2, reentrantLock.getHoldCount());
        assertEquals(true, reentrantLock.isLocked());

        reentrantLock.unlock();
        assertEquals(1, reentrantLock.getHoldCount());
        assertEquals(true, reentrantLock.isLocked());

        reentrantLock.unlock();
        assertEquals(0, reentrantLock.getHoldCount());
        assertEquals(false, reentrantLock.isLocked());

        4. Supports fairness
        A synchronized block doesn’t support fairness. Any thread can acquire the lock once released, and no preference
        can be specified. We can achieve fairness within the Lock APIs by specifying the fairness property.
        It makes sure that the longest waiting thread is given access to the lock.

        An unfair lock does not guarantee the order in which threads waiting to lock the lock will be given access to
        lock it. The ReentrantLock behavior is unfair by default. However, you can tell it to operate in fair mode via
        its constructor. Here is an example of creating a ReentrantLock instance using fair mode:
        ReentrantLock lock = new ReentrantLock(true);

        5. lockInterruptibly
        ReentrantLock provides a method called lockInterruptibly(), which can be used to interrupt thread when it
        is waiting for lock. Similarly tryLock() with timeout can be used to timeout if lock is not available in
        certain time period.

        Example of Counter Class

        public class CounterLock {

            private long counter = 0;
            private Lock lock = new ReentrantLock();
            public void inc() {
                try {
                    lock.lock();
                    this.counter++;
                } finally {
                    lock.unlock();
                }
            }
            public long getCounter() {
                try {
                    lock.lock();
                    return this.counter;
                } finally {
                    lock.unlock();
                }
            }
        }

        We used a single lock variable for both write and read operations, only a single thread can read or write the
        counter at a given time.

        ReadWriteLock
        is an advanced thread lock mechanism. It allows multiple threads to read a certain resource, but only
        one to write it, at a time.

        Read Lock: If no threads have locked the ReadWriteLock for writing, and no thread have requested
        a write lock (but not yet obtained it). Thus, multiple threads can lock the lock for reading.
        Write Lock: If no threads are reading or writing. Thus, only one thread at a time can lock the lock for writing.

        public class CounterLock {

              private int counter = 0;
              private ReadWriteLock lock = new ReentrantReadWriteLock();

              // only one writer can enter this section,
              // and only if no threads are currently reading.
              public void increment() {
                lock.writeLock().lock();
                try {
                  counter += 1;
                } finally {
                  lock.writeLock().unlock();
                }
              }

              // multiple readers can enter this section
              // if not locked for writing, and not writers waiting
              // to lock for writing.
              public int getCounter() {
                lock.readLock().lock();
                try {
                  return counter;
                } finally {
                  lock.readLock().unlock();
                }
              }
        }

        Notice : how the ReadWriteLock actually internally keeps two Lock instances. One guarding read access, and one
        guarding write access.
        With a ReadWriteLock, the read lock is shared while the write lock is exclusive.
        A ReadWriteLock works best when there are many reads and few writes.

        Why Not to use Reentrant Lock ?
        Failing to use finally to release a Lock is a ticking time bomb. When it goes off, you will have a hard time
        tracking down its origin as there will be no record of where or when the Lock should have been released.
        This is one reason not to use ReentrantLock as a blanket substitute for synchronized: it is more “dangerous”
        because it doesn’t automatically clean up the lock when control leaves the guarded block.

        Polled and timed lock acquisition
        With intrinsic locks, a deadlock is fatal—the only way to recover is to restart the application, and the only
        defense is to construct your program so that inconsistent lock ordering is impossible.

        Using timed or polled lock acquisition (tryLock) lets you regain control if you cannot acquire all the required
        locks, release the ones you did acquire, and try again (or at least log the failure and do something else).



}


 */
public class Sample {

    final Lock lock = new ReentrantLock();
    Semaphore mutex = new Semaphore(1);

    long __(int n) throws InterruptedException {
        lock.lock();
        try{
            if(n <=1) return 1;
            return __(n-1) + 1;
        } finally {
            lock.unlock();
        }
    }

    long f(int n) throws InterruptedException {
        mutex.acquire();
        System.out.println(n);
        try{
            if(n <=1) return 1;
            return __(n-1) + 1;
        } finally {
            mutex.release();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Sample sample = new Sample();
        System.out.println(sample.__(100));

        //System.out.println(sample.f(2));
    }
}
