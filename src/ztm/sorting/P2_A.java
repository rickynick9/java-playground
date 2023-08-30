package ztm.sorting;

/*
Same problem as P2 but different approach
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class P2_A {
    static class AnagramComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return sortChars(o1).compareTo(sortChars(o2));
        }
        private String sortChars(String str) {
            char[] arr = str.toCharArray();
            Arrays.sort(arr);
            return new String(arr);
        }
    }

    public static void main(String[] args) {
        List<String> input = Arrays.asList("eat","tea","tan","ate","nat","bat");
        input.sort(new AnagramComparator());
        input.stream().forEach(s -> System.out.print(" "+s+" "));
        //  eat  tea  ate  bat  tan  nat
    }

}
