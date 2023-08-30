package ztm.Section6;

import java.util.Map;

/*
Given an integer array nums, find the  subarray with the largest sum, and return its sum.
Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
Output: 6
Explanation: The subarray [4,-1,2,1] has the largest sum 6.
 */

/*
Iteration 1: sum = -2 max = -2 sum = 0
Iteration 2: sum = 1 ,max = 1
Iteration 3: sum = -2, max = 1 sum = 0
Iteration 4:  sum = 4, max = 4
Iteration 5: sum = 3, max = 4
Iteration 6: sum = 5, max = 5
Iteration 7: sum = 6, max = 6
Iteration 8: sum = 1, max = 6
Iteration 9: sum = 5, max = 6

{-6, -2, -8, -5, 4, -1, 6}

Iteration 1: sum = -6 max = -6 sum =0
Iteration 2: sum = -2 max = -2 sum =0
Iteration 3: sum = -8 max = -2 sum =0
Iteration 4: sum = -5 max = -2 sum =0
Iteration 5: sum = 4 max = 4
Iteration 6: sum = 3 max = 4
Iteration 7: sum = 9 max = 9
 */

/*
Kadane’s Algorithm
the subarray with negative sum is discarded (by assigning max_ending_here = 0 in code).
we carry subarray till it gives positive sum.

 */
public class MaxSubArray {
    public static void main(String[] args) {
        int[] arr = {-2,1,-3,4,-1,2,1,-5,4};
        int maxSum = maxSubArray(arr);
        System.out.println(maxSum);
    }

    public static int maxSubArray(int[] arr) {
        int max = Integer.MIN_VALUE, sum = 0;

        for(int i=0; i< arr.length; i++) {
            sum +=arr[i];
            max = Math.max(sum, max);

            if(sum<0) sum = 0;

        }
        return max;
    }
}
