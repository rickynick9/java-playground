package ztm.Section6;

import java.util.Arrays;

/*
Given an integer array nums, move all 0's to the end of it while maintaining the relative order of the non-zero elements.
Note that you must do this in-place without making a copy of the array.

Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]

swap(0, 1) -> [1,0,0,3,12]
swap(0, 3) -> [1, 0, 3, 0, 12]
 */
public class MoveZero {

    public static void main(String[] args) {
        int[] arr1 = {0,1,0,3,12};

        int[] arr = {4,1,0,3,12,0,15,0,18};
        moveZeroToEnd(arr);
        Arrays.stream(arr).forEach(System.out::println);
    }

    public static void moveZeroToEnd(int[] arr) {
        int snowBallsizse = 0;
        for(int i =0; i< arr.length; i++) {
            if(arr[i] == 0) {
                snowBallsizse++;
            } else if(snowBallsizse > 0) {
                int temp = arr[i];
                arr[i] = 0;
                arr[i - snowBallsizse] = temp;

                // [0,1,0,3,12]
                /*
                 Iteration1 : snowballSize = 1
                 Iteration 2: snowballSize = 1, swap(i, i-snowBallSize) swap (1, 0)
                 [1,0,0,3,12]
                 Iteration 3: snowballSize = 2,
                 Iteration 4: snowBallSize = 2, swp(3, 1)
                 [1,3,0,0,12]
                 Iteration 5:  swap(4, 2)
                 */
            }
        }
    }
}
