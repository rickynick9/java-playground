package ztm.sorting;

import java.util.Arrays;

/*
Peaks and Valleys

Sort given array
sorted array will be less < medium < large
To form peak and valley we need to swap mediam and less i.e i and i-1
Increment i by 2
 */
public class P9 {

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    public static void peaksAndValley(int[] arr) {
        Arrays.sort(arr);
        for(int i=1; i< arr.length; i=i+2) {
            swap(arr, i, i-1);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        peaksAndValley(arr);
        Arrays.stream(arr).forEach(e -> System.out.print(" "+e+" "));
    }


}
