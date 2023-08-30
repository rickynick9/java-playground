package ztm.section7;

import java.util.HashMap;
import java.util.Map;

/*
Given an array, find out first recurring number. The number that is repating itself.
Given array = [2, 5, 1, 2, 3, 5, 1, 2, 4]
o/p = 2

 */
public class FirstRecurringCharacter {
    public static void main(String[] args) {
        int[] arr = {2, 5, 1, 2, 3, 5, 1, 2, 4};
        int number = firstRecurring(arr);
        System.out.println("First recurring number : "+number);
    }

    public static int firstRecurring(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0; i< arr.length; i++) {
            if(map.get(arr[i]) == null) {
                map.put(arr[i], i);
            } else {
                return arr[i];
            }
        }

        return -1;
        //return Integer.parseInt(Double.POSITIVE_INFINITY);
    }

    public static int firstRecurringNestedLoop(int[] arr) {
        for(int i=0; i< arr.length; i++) {
            for(int j=i+1; j< arr.length; j++) {
                if(arr[i] == arr[j]) {
                    return arr[i];
                }
            }
        }
        return -1;
    }
}
