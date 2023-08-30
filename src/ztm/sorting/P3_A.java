package ztm.sorting;

import java.util.Arrays;

/*
Rotate an array without using extra space.

Steps :
- Reverse entire array
- Reverse first k elements
- Reverse remaining elements
 */
public class P3_A {

    public static void rotateRight(int[] nums, int k) {
        // Ensure k is within array bounds
        k %= nums.length;
        // Reverse entire array
        reverse(nums, 0, nums.length - 1);
        // Reverse first k elements
        reverse(nums, 0, k - 1);
        // Reverse remaining elements
        reverse(nums, k, nums.length - 1);
    }

    // Exactly reverse order in case of rotate left
    public static void rotateLeft(int[] nums, int k) {
        // Ensure k is within array bounds
        k %= nums.length;


        reverse(nums, k, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, 0, nums.length - 1);

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

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6,7};
        int degreeOfRotation = 3;
        rotateRight(arr, degreeOfRotation);
        Arrays.stream(arr).forEach(e -> System.out.print(" "+e+" "));

    }
}
