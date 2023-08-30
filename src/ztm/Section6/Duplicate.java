package ztm.Section6;

import java.util.HashSet;
import java.util.Set;

/*
Given an integer array nums, return true if any value appears at least twice in the array, and return false if every
element is distinct.

Input: nums = [1,2,3,1]
Output: true

 */
public class Duplicate {

    public static void main(String[] args) {
        int[] arr = {1,2,3,1};
        System.out.println(checkDuplicate(arr));
    }

    private static boolean checkDuplicate(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for(int i=0; i< arr.length; i++) {
            set.add(arr[i]);
        }
        return set.size() != arr.length;
    }

}
