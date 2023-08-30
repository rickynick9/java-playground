package ztm.section8.queue;

import java.util.concurrent.TimeUnit;

/*

A Queue that additionally supports operations that wait for the queue to become non-empty when retrieving an element,
and wait for space to become available in the queue when storing an element.
BlockingQueue methods come in four forms, with different ways of handling operations that cannot be satisfied immediately,
but may be satisfied at some point in the future:
one throws an exception,
the second returns a special value (either null or false, depending on the operation),
the third blocks the current thread indefinitely until the operation can succeed,
and the fourth blocks for only a given maximum time limit before giving up.

            Throws exception    Returns special value       Blocks      Times out
Insert      add(e)              offer(e)                    put(e)      offer(e, time, unit)
Remove      remove()            poll()                      take(e)     poll(time, unit)
Examine     element()           peek()
 */
public interface BlockingQueueI<E> extends IterableI<E> {

    //boolean add(E e); // add and offer is same
    boolean offer(E e);
    void put(E e) throws InterruptedException; //If thread was interuppted while waiting
    boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException; //If thread was interrupted while waiting
    // for specified time

    //E remove();
    E poll();
    E take() throws InterruptedException;
    E poll(long timeout, TimeUnit unit) throws InterruptedException;

    //E element();
    E peek();
}
