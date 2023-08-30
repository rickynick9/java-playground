package ztm.section8.priorityqueue;

public class UnorderedMaxPQ<k extends Comparable<k>> {

    private k[] pq;
    private int count; // no of elements

    public UnorderedMaxPQ(int capacity) {
        pq = (k[]) new Comparable[capacity];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void insert(k item) {
        pq[count++] = item;
    }

//    public k delMax() {
//        int max = 0;
//        for(int i=0; i<count; i++) {
//            //if(less(max, i)) max = i;
//        }
//    }


}
