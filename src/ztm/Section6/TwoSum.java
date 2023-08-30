package ztm.Section6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 */
public class TwoSum {
    public static void main(String[] args) {
        int[] arr = {2,7,11,15,4,9,8};
        int target = 10;
        int[] indexes = twoSum(arr, target);
        Arrays.stream(indexes).forEach(System.out::println);
    }

    public static int[] twoSum(int[] arr, int target) {
        // number and position
        Map<Integer, Integer> map = new HashMap<>();
        for(int i= 0; i< arr.length; i++) {
            if(map.get(arr[i]) !=null) {
                return new int[]{map.get(arr[i]), i};
            } else {
                map.put(target - arr[i], i);
            }
        }
        return new int[0];
    }
}
