package ztm.section13;

import ztm.section8.queue.DequeI;

/*
https://www.java67.com/2018/03/how-to-implement-radix-sort-in-java.html
http://www.btechsmartclass.com/data_structures/radix-sort.html

 */

import java.util.*;

public class CountingSort {

    final static int RADIX = 10;

    private static int getMax(int[] arr) {
        int max = 0;
        for(int i=0; i< arr.length; i++) {
            if(arr[i] > max)
                max = arr[i];
        }
        return max;
    }

    private static int[] countSortForRadix(int[] arr) {
        int placement = 1;

        int tmp;
        int max = getMax(arr);
        boolean maxLength = false;


        Deque<Integer>[] bucket = new ArrayDeque[10];
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new ArrayDeque<>();
        }

        while (!maxLength) {
            maxLength = true;

            for (Integer i : arr) {
                tmp = i / placement;
                bucket[tmp % 10].offer(i);
                if (maxLength && tmp > 0) {
                    maxLength = false;
                }

            }

            System.out.println("Bucket length :"+bucket.length);
            // empty lists into input array
            int k =0;
            for (int i = 0; i < bucket.length; i++) {

                while (bucket[i].size() > 0) {
                    arr[k++] = bucket[i].poll();
                }
            }

            placement *= RADIX;

        }
        return arr;
    }


    private static int[] countSort(int[] arr) {
        int k = 0;
        int max = getMax(arr);
        int count[] = new int[max+1];
        int sortedArr[] = new int[arr.length];

        for(int i=0; i<arr.length; i++) {
            //System.out.println(arr[i]);
            count[arr[i]]++;
        }

        //System.out.println("Count array :");
        //Arrays.stream(count).forEach(e -> System.out.print(" "+e+" "));

        for(int i=0; i< count.length; i++) {
            while (count[i] > 0) {
                sortedArr[k++] = i;
                count[i] = count[i] - 1;
            }
        }
        return sortedArr;
    }

    public static void main(String[] args) {
        //int[] input = {6, 3, 3, 10, 4, 5, 5, 5, 8, 7, 2};

        int[] input = {326,311,3,4,120,65};


        System.out.println("\n");
        //Arrays.stream(countSort(input)).forEach(e -> System.out.print(" "+e+" "));
        Arrays.stream(countSortForRadix(input)).forEach(e -> System.out.print(" "+e+" "));
    }
}
