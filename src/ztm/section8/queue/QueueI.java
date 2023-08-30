//package ztm.section8.queue;
//
///*
//            Throws exception    Returns special value
//Insert      add(e)              offer(e)
//Remove      remove()            poll()
//Examine     element()           peek()
//
//The offer method inserts an element if possible, otherwise returning false. This differs from the Collection.add method,
//which can fail to add an element only by throwing an unchecked exception. The offer method is designed for use when
//failure is a normal, rather than exceptional occurrence, for example, in fixed-capacity (or "bounded") queues.
//
// The remove() and poll() methods remove and return the head of the queue.
// The remove() and poll() methods differ only in their behavior when the queue is empty: the remove() method throws an
// exception, while the poll() method returns null.
//
// The element() and peek() methods return, but do not remove, the head of the queue.
//
// */
//public interface QueueI<E> {
//    boolean add(E e);
//    boolean offer(E e);
//
//    E remove();
//    E poll();
//
//    E element();
//    E peek();
//
//
//
//}
