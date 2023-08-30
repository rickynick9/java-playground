package ztm.Section6;

import java.util.Arrays;

/*
Given an integer array nums, rotate the array to the right by k steps, where k is non-negative.

Input: nums = [1,2,3,4,5,6,7], k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]

 */
public class RotateArray {

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6,7};
        int k = 3;
        rotate(arr, k);
        Arrays.stream(arr).forEach(System.out::println);

    }

    public static void rotate(int[] nums, int k) {
        // Ensure k is within array bounds
        k %= nums.length;
        // Reverse entire array
        reverse(nums, 0, nums.length - 1);
        // Reverse first k elements
        reverse(nums, 0, k - 1);
        // Reverse remaining elements
        reverse(nums, k, nums.length - 1);

    }

    public static void reverse(int nums[], int start, int end){
        // While start index is less than end index
        int temp;
        while(start < end){
            // Swap elements at start and end indices
            temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            // Increment start index and decrement end index
            start++;
            end--;
        }
    }
}
