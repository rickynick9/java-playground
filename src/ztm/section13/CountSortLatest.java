package ztm.section13;

import java.util.Arrays;

/*
Ref : https://levelup.gitconnected.com/visualizing-designing-and-analyzing-the-counting-sort-algorithm-dff56ea01e93

 */
public class CountSortLatest {

    private static int getMax(int[] arr) {
        int max = 0;
        for(int i=0; i< arr.length; i++) {
            if(arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }
    private static int[] countSort(int[] arr) {
        int max = getMax(arr);
        int[] count = new int[max+1];
        int[] output = new int[arr.length];
        //build count array
        for(int i=0; i<arr.length; i++)
            count[arr[i]]++;

        //cumulative sum
        for (int i=1; i<=max; i++)
            count[i] = count[i] + count[i-1];

        //based on cumulative sum, add elements in output array
        for(int j = arr.length -1; j >= 0; j--) {
            int pos = count[arr[j]];
            output[pos -1] = arr[j];
            //decrement the count value
            count[arr[j]]--;
        }
        return output;
    }



    public static int[] countingSort(int[] inputArray) {
        //find max value of inputArray
        int max = getMax(inputArray);

        //initialize count array with length=(max+1) and all elements as 0
        int countArray[] = new int[max + 1];


        //find the no of occurrences of each unique element and store
        //in count array at their respective indices.
        for (int i = 0; i < inputArray.length; i++) {
            countArray[inputArray[i]]++;
        }

        //find cumulative sum in the count array. This will be used to store
        //the elements from input array to output array at correct position
        for (int i = 1; i <= max; i++) {
            countArray[i] += countArray[i - 1];
        }

        int outputArray[] = new int[inputArray.length];
        //store the elements from input array to output array using cumulative index
        for (int j = (inputArray.length - 1); j >= 0; j--) {
            outputArray[countArray[inputArray[j]] - 1] = inputArray[j];
            countArray[inputArray[j]]--;
        }

        return outputArray;
    }


    public static void main(String[] args) {
        int inputArray[] = {2, 5, 3, 0, 2, 3, 0, 3};
        Arrays.stream(countSort(inputArray)).forEach(e -> System.out.print(" "+e+" "));

    }
}
