package ztm.Section6;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReverseString {

    public static void main(String[] args) {
        String str = "My Name Is Nishant";
        System.out.println(reverseStringV2(str));
    }

    public static String reverseStringV1(String str) {
        // check input
        if(str.length() < 2) {
            System.out.println("Cannot reverse");
        }

        char[] stringArray = str.toCharArray();
        char[] backward = new char[str.length()];

        for(int i = str.length() -1, j =0; i >= 0; i--) {
            backward[j] = stringArray[i];
            j++;
        }

        return String.valueOf(backward);
    }

    public static String reverseStringV2(String str) {
        return nullCheck(str) ? null : Stream.of(str)
                .map(s -> new StringBuilder(s).reverse())
                .collect(Collectors.joining());
    }

    public static String reverseStringV3(String str) {
        char[] charArray = str.toCharArray();
        return IntStream.range(0, str.length() -1)
                .map(i -> charArray[str.length() -1 -i])
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    /*
    Note :
    List<String> asList = stringStream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    But I expected this
    List<String> asList = stringStream.collect(ArrayList::new, ArrayList::add );

    The combiner is used when your Stream is parallel, since in that case several threads collect elements of the
    Stream into sub-lists of the final output ArrayList, and these sub-lists have to be combined to produce the final
    ArrayList.
     */

    public static boolean nullCheck(String str) {
        if(str == null) {
            return true;
        } else {
            return false;
        }
    }


}
