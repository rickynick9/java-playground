package ztm.sorting;

/*
Given a sorted array of n integers that has been rotated an unknown number of times, write code to find an element
in the array. You may assume that array was originally sorted in increasing order.

Observation after rotating the array
One half of the array must be ordered normally (increasing order).
We can look at normal ordered half to determine whether to search left of right half.
 */
public class P4 {

    private static int search(int[] arr, int left, int right, int x) {

        System.out.println("left :"+left+" right: "+right);
        if (right < left)
            return -1;

        int mid = (left + right) / 2;
        if(x == arr[mid])
            return x;

        if (arr[left] < arr[mid]) {
            //this means left side of array is in increasing order
            if (x >= arr[left] && x < arr[mid]) {
                return search(arr, left, mid - 1, x);
            } else {
                return search(arr, mid + 1, right, x);
            }

        } else if (arr[left] > arr[mid]) {
            // this means right side of the array is in increasing order
            if (x > arr[mid] && x <= arr[right]) {
                return search(arr, mid + 1, right, x);
            } else {
                return search(arr, left, mid - 1, x);
            }
        }

        return -1;
    }

    public static int search(int[] nums, int target) {
        int low = 0, high = nums.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[low] <= nums[mid]) {
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int number = 4; //number to search
        int arr[] = {3, 4, 5, 6, 7, 1, 2};
        //array above is rotated 6 times. original array : {1, 2, 3, 4, 5, 6, 7}

        System.out.println(search(arr, 0, arr.length - 1, number));

        System.out.println(search(arr, 9));




    }
}
