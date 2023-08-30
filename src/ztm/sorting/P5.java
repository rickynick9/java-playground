package ztm.sorting;

/*
You are given an array list data structure Listy which lacks a size method. It does, however, have elementAt(i)
method that returns the element at i index in O(1) time.

Given Listy which contains positive sorted integer, find index at which element x occurs.

 */
public class P5 {

    private static int binarySearch(Listy list, int low, int high, int x) {
        if(low > high)
            return -1;
        int mid = (low + high) / 2;

        if(list.elementAt(mid) == x)
            return mid;

        if(x >= list.elementAt(low) && x< list.elementAt(mid))
            return binarySearch(list, low, mid - 1, x);
        if(x > list.elementAt(mid) && x <= list.elementAt(high))
            return binarySearch(list, mid + 1, high, x);

        return -1;
    }

    private static int search(Listy listy, int x) {
        // we know elementAt(i) would return -1 when i is too small or too large
        int i =1;
        while(listy.elementAt(i) != -1 && listy.elementAt(i) < x) {
                i = i * 2;
        }

        // i/2 as loe because we doubled the index just before the number we are looking for
        return binarySearch(listy, i/2, i, x);
    }

    public static void main(String[] args) {
        Listy listy = new Listy(7);
        int arr[] = {11, 25, 37, 42, 59, 60, 76};
        listy.initialize(arr);

        System.out.println(search(listy, 60));

        // There is no way to find out the length of Listy data structure
        // In order to perform binary search we must know the length of data structure
        // We should find out the length in logarithmic time and perform binary search


    }

    static class Listy {
        private int[] arr;
        private int size;

        Listy(int size) {
            this.size = size;
            this.arr = new int[size];
        }

        public void initialize(int[] arrCopy) {
            if(arrCopy.length > size)
                throw new ArrayIndexOutOfBoundsException("Index out of bound!");

            System.arraycopy(arrCopy, 0, arr, 0, size);
        }

        //returns element at index 1;
        public int elementAt(int i) {
            if(i < 0 || i> size)
                return -1;
            return arr[i];
        }
    }
}
