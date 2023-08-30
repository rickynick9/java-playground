package ztm.section4;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FindCommonInArrays {

    public static void main(String[] args) {
        char[] array1 = {'a', 'b', 'c', 'x'};
        char[] array2 = {'z', 'y', 'i'};
        System.out.println("Common : "+findCommonV3(array1, array2));
    }

    // Time complexity : O(m * n)
    // Space complexity : O(1) because we have just added 2 variables inside function
    private static boolean findCommon(char[] array1, char[] array2) {
        //Stream<Character> stream1 = IntStream.range(0, array1.length).mapToObj(i -> array1[i]);
        //Stream<Character> stream2 = IntStream.range(0, array2.length).mapToObj(i -> array2[i]);

        for(char a : array1) {
            for(char b: array2) {
                if(a == b)
                    return true;
            }
        }
        return false;
    }

    /*
    Time complexity : O (n + m)
    Space complexity : O(n) we have created a Map and copied all elements from array to map
    If space is not a concern then this solution is faster
     */
    private static boolean findCommonV2(char[] array1, char[] array2) {
        /*
        If we are able to convert array1 to object of map
        {
            "a" : true,
            "b" : true,
            "c" : true,
            "x" : true
        }
        Then we can just iterate through array 2 and check
        Map.containsKey(array2[index])
         */
        /*
        Approach :
        Loop through first array and create HashMap : O(m)
        Loop through second array and check if key exists in HashMap O(n + m)

        Time complexity : O(n + 2m) ~ O (n + m)
         */
        Map<Character, Boolean> arrayToMap = new HashMap<>();
        for(char c: array1) {
            arrayToMap.put(c, true);
        }

        for(char c : array2) {
            Boolean val = arrayToMap.get(c);
            if(val !=null && val) // instead of containsKey use get. get has time complexity of O(1)
                return true;
        }
        return false;
    }

    private static boolean findCommonV3(char[] array1, char[] array2) {
        Stream<Character> stream1 = IntStream.range(0, array1.length).mapToObj(i -> array1[i]);
        Stream<Character> stream2 = IntStream.range(0, array2.length).mapToObj(i -> array2[i]);
        Map<Character, Boolean> arrayToMap = stream1.collect(Collectors.toMap(s1 -> s1, s1 -> true));

        //List<Character> result = stream2.filter(arrayToMap::containsKey).toList();
       return stream2.anyMatch(arrayToMap::containsKey);
    }
}
