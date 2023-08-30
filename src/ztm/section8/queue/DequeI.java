package ztm.section8.queue;

public interface DequeI<E> extends IterableI<E> {

    // *** Queue methods ***
    boolean offer(E element);
    E poll();
    E peek();

    // *** Stack methods ***
    void push(E element);
    E pop();

    int size();

    IteratorI<E> iterator();

    IteratorI<E> descendingIterator();

}
