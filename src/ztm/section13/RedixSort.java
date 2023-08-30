package ztm.section13;

import java.util.Arrays;

/*
https://levelup.gitconnected.com/visualizing-designing-and-analyzing-the-radix-sort-algorithm-ca7adb578390
 */
public class RedixSort {

    private static int getMax(int[] arr) {
        int max = 0;
        for(int i=0; i< arr.length; i++) {
            if(arr[i] > max)
                max = arr[i];
        }
        return max;
    }

    public static int[] countSort(int[] arr, int place) {
        int[] count = new int[10];

        //populate count array
        for(int i=0; i<arr.length; i++) {
            int placeValue = (arr[i] / place) % 10;
            count[placeValue]++;
        }
        //find cumulative(increased) sum in count array
        for(int i=1; i<count.length; i++) {
            count[i] = count[i] + count[i-1];
        }

        //Store the elements from input array to output array using count array.
        int[] output = new int[arr.length];
        for(int j =arr.length -1; j>=0; j--) {
            int pv = (arr[j] / place) % 10;
            int index = count[pv] -1;
            count[pv]--;
            output[index] = arr[j];
        }

        return output;

    }

    // place -> one's place, 10's place 100's place etc
    public static int[] redixSort(int[] arr) {
        int max = getMax(arr);
        int[] count = new int[10]; // size of count array 10 because digits can be from 0 to 9

        //find number of digits in max element
        int d = 0;
        while (max > 0) {
            d++;
            max /= 10;
        }

        //Use counting cort d no of times
        int place = 1;//unit place
        for (int i = 0; i < d; i++) {
            //System.out.print("iteration no = "+(i+1)+" ");
            arr = countSort(arr, place);
            place *= 10;//ten's , hundred's place etc
        }

        return arr;
    }

    public static void main(String[] args) {
        int inputArray[] = {10, 21, 17, 34, 44, 11, 654, 123};
        Arrays.stream(redixSort(inputArray)).forEach(e -> System.out.print(" "+e+" "));
    }
}
