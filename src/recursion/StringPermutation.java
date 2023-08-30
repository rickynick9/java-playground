package recursion;


/*
Since the string is immutable in Java, the idea is to convert the string into a character array.
Then we can in-place generate all permutations of the given string using backtracking by swapping
each of the remaining characters in the string with its first character and then generating all the
permutations of the remaining characters using a recursive call.

https://www.techiedelight.com/generate-permutations-string-java-recursive-iterative/

 */
public class StringPermutation {

    // Utility function to swap two characters in a character array
    private static void swap(char[] ch, int i, int j)
    {
        char temp = ch[i];
        ch[i] = ch[j];
        ch[j] = temp;
    }

    // Recursive function to generate all permutations of a string
    private static void permutations(char[] ch, int currentIndex) {
        if (currentIndex == ch.length - 1) {
            System.out.println(String.valueOf(ch));
        }

        for (int i = currentIndex; i < ch.length; i++) {
            swap(ch, currentIndex, i);
            permutations(ch, currentIndex + 1);
            swap(ch, currentIndex, i);
        }
    }

    // generate all permutations of a string in Java
    public static void main(String[] args) {
        String s = "ABC";
        permutations(s.toCharArray(), 0);
    }
}
