package ztm.section4;

import java.util.HashSet;
import java.util.Set;

public class HasPairWithSum {
    public static void main(String[] args) {
        int arr[] = {7, 2, 3, 8, 5, 9};
        System.out.println("Has pair with sum : 18 -->"+hasPairWithSumV2(arr, 18));
    }

    private static boolean hasPairWithSumV1(int[] arr, int sum) {
        for(int i =0; i< arr.length; i++) {
            for(int j =0; j< arr.length; j++) {
                if(i != j && (arr[i] + arr[j] == sum)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean hasPairWithSumV2(int[] arr, int sum) {
        Set<Integer> set = new HashSet<>();
        for(int i =0; i< arr.length; i++) {
            if(set.contains(arr[i])) {
                return true;
            }
            set.add(sum - arr[i]);
        }
        return false;
    }
}
