package ztm.Section6;

import java.util.Arrays;

public class MergeSorted {

    //mergeSortedArray([0, 3, 4, 31], [4, 6, 30])
    public static void main(String[] args) {
        int[] arr1 = {0, 3, 5, 31}, arr2 = {4, 6, 30};
        int[] mergedArr = merge(arr1, arr2);
        Arrays.stream(mergedArr).forEach(System.out::println);
    }

    private static int[] merge(int[] arr1, int[] arr2) {
        int mergedArray[] = new int[arr1.length + arr2.length];
        int arr1Pos = 0, arr2Pos =0, mergedArrayPos = 0;
        int arr1Length = arr1.length;
        int arr2Length = arr2.length;

        if(arr1Length == 0) {
            return arr2;
        }
        if(arr2Length == 0) {
            return arr1;
        }
        // {0, 3, 4, 31}, arr2 = {4, 6, 30};
        while (arr1Pos < arr1Length && arr2Pos < arr2Length) {
            if(arr1[arr1Pos] < arr2[arr2Pos]) {
                mergedArray[mergedArrayPos++] = arr1[arr1Pos++];
            } else {
                mergedArray[mergedArrayPos++] = arr2[arr2Pos++];
            }
        }
        //These two loops are for the remaining items
        while(arr1Pos < arr1Length) {
            mergedArray[mergedArrayPos++] = arr1[arr1Pos++];
        }

        while (arr2Pos < arr2Length) {
            mergedArray[mergedArrayPos++] = arr2[arr2Pos++];
        }

        return mergedArray;
    }
}
