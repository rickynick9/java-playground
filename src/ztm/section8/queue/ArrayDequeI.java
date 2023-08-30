package ztm.section8.queue;

import java.util.Arrays;



public class ArrayDequeI<E> implements DequeI<E> {

    transient Object[] elements;

    transient int head;

    transient int tail;

    private int count;

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    public ArrayDequeI() {
        elements = new Object[16 + 1];
    }

    public ArrayDequeI(int capacity) {
        elements = new Object[capacity + 1];
    }

    private int inc(int i, int modulus) {
        //System.out.println("incrementing tail");
        if(++i >= modulus) i =0;
        return i;
    }

    private void grow() {
        final int oldCapacity = elements.length;
        int newCapacity = oldCapacity * 2;
        //System.out.println("New capacity is :"+newCapacity);
        System.out.println("Head is :"+head);
        System.out.println("Count is : "+count);
        System.out.println("Tails is : "+tail);
        final Object[] es = new Object[newCapacity];
        //System.out.println("new length of es :"+es.length);
        System.arraycopy(elements, head, es, 0, count - head);
        //System.arraycopy(elements, 0, es, oldCapacity, tail);
        tail = elements.length;

        //Arrays.stream(es).forEach(e-> System.out.println("es array :"+e));
        elements = Arrays.copyOf(es, newCapacity);
   }

    private void addLast(E element) {

        final Object[] es = elements;
        es[tail] = element;
        count++;
        tail++;
        if(tail >= es.length) tail = 0;

        if(count == es.length)
            grow();



        //grow when head and tail are equal. tail is circularly incemented
        // when head and tail are equal, means queue is full
        //if (head == (tail = inc(tail, es.length)))
            //grow();
    }


    @Override
    public boolean offer(E element) {
        addLast(element);
        return true;
    }

    private E pollFirst() {
        E element = (E) elements[head];
        elements[head] = null;
        head++;
        count--;
        if(head > tail)
            head = 0; tail = 0;
        return element;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public void push(Object element) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public IteratorI iterator() {
        return null;
    }

    @Override
    public IteratorI descendingIterator() {
        return null;
    }

    public static void main(String[] args) {
        ArrayDequeI<Integer> arrayDeque = new ArrayDequeI<>(5);
        arrayDeque.offer(1);
        arrayDeque.offer(2);
        arrayDeque.offer(3);
        arrayDeque.offer(4);
        arrayDeque.offer(5);
        //arrayDeque.offer(6);

        System.out.println("poll --> "+arrayDeque.poll());
        System.out.println("poll --> "+arrayDeque.poll());

        arrayDeque.offer(6);
        arrayDeque.offer(7);
        arrayDeque.offer(8);



        Arrays.stream(arrayDeque.elements).forEach(e -> System.out.println(e));

    }
}
