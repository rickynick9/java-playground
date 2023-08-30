package ztm.sorting;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class P2 {
/*
Given an array of strings strs, group the anagrams together. You can return the answer in any order.
An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase, typically using all
the original letters exactly once.

Example 1:

Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]

Approach : Group all anagrams in a hash map
key of hash map should be sorted characters of an anagram
aet -> ["ate","eat","tea"]

Since the order does not matter so we can iterate over Hashmap and add avlues to an array

You can use flatMap to flatten the internal lists (after converting them to Streams) into a single Stream, and then collect the result into a list:

List<List<Object>> list = ...
List<Object> flat =
    list.stream()
        .flatMap(List::stream)
        .collect(Collectors.toList());

*/

    private static List<String> groupAnagrams(List<String> input) {
        //Map<String, List<String>> anagramMap = new HashMap<>();

//        input.stream().forEach(str -> {
//            String key = sortedString(str);
//            anagramMap.put(key, new ArrayList<>());
//            anagramMap.get(key).add(str);
//        });

        //input.stream().collect(Collectors.toMap(s -> s , s -> s.length()));

//        Map<String, List<String>> anagramMap = input.stream()
//                .collect(Collectors.groupingBy(str -> sortedString(str)));

        Map<String, List<String>> anagramMap = input.stream()
                .collect(Collectors.groupingBy(P2::sortChars));

        return anagramMap.values().stream().flatMap(List::stream).collect(Collectors.toList());

    }

    private static String sortChars(String str) {
        char[] arr = str.toCharArray();
        Arrays.sort(arr);
        return new String(arr);
    }

    public static void main(String[] args) {
        List<String> input = Arrays.asList("eat","tea","tan","ate","nat","bat");

        groupAnagrams(input).stream().forEach(s-> System.out.print(" "+s+" "));
    }
}
