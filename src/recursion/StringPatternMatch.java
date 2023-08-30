package recursion;

import java.util.HashSet;
import java.util.Set;

// String - COCACOLA
// Pattern - CO
// Time complexity : O(mn)

/*

String : a b c d a b c a b c d f
Pattern : a b c d f

We will have an 'i' pointer that will move over the "string" and a 'j' pointer that will iterate over the "pattern".
Initially, both i and j will be on index 0.

a	b	c	d	a	b	c	a	b	c	d	f
0	1	2	3	4	5	6	7	8	9	10	11

a	b	c	d	f
0	1	2	3	4

Now, when i = 0, and j = 0, we see that string[i] is equal to pattern[j]. Which means that there is a possibility of
finding the pattern. Since there is a match, we increment both i and j.

So now i and j = 1. Again, at string[1] and pattern[1] we see that there is a match, so we increment both i and j.
This goes on till index 3, i.e. when we reach the letter 'd', we have a match. As soon as we increment after the match,
we find that at i = j = 4, there is a mismatch.

we are going to start matching from the first index of the pattern string again. This means that j
will be set to 0 once more.

What happens to the 'i' pointer?
It goes back to the first index, to try and find a match. We started from the 0th index, so we just moved it by 1.

Do you see the problem here in the simple algorithm?
We are performing unnecessary computations, as in we're repeatedly looking for a match in elements that we have
already traversed, and this makes the algorithm slow.

The basic algorithm will now check for a match between j = 0, and i = 1.
Pattern[j] is not equal to string[i] in this case, so we increment only the 'i' pointer.

i = 1, j = 0	mismatch
i = 2, j = 0	mismatch
i = 3, j = 0	mismatch

Till index 4, we have mismatches. But as soon as we increment the i pointer to point to string[4], we have a match.
So this time, we increment both i and j.

i = 4, j = 0	match
i = 5, j = 1	match
i = 6, j = 2	match
i = 7, j = 3	mismatch


*/
public class StringPatternMatch {

    public static void main(String[] args) {
        String text = "abcdabcabcdf";
        String pattern = "abcdf";
        char[] textArr = text.toCharArray();
        char[] patternArr = pattern.toCharArray();
        Set<Integer> patternPos = new HashSet<>();

        int textLength = text.length();
        int patternLength = pattern.length();

        for(int i=0; i< textLength; i++) {
            for(int j=0; j< patternLength; j++) {
                if(textArr[i+j] != patternArr[j]) {
                    break;
                }
                if(j == patternLength -1) {
                    patternPos.add(i);
                }
            }
        }

        if(patternPos.isEmpty()) {
            System.out.println("No pattern found");
        } else {
            patternPos.forEach(pos -> System.out.println("Pattern position found at : "+pos));
        }
    }

    public static void testMethod() {
        String text = "abcdabcabcdf";
        String pattern = "abcdf";
        char[] textArr = text.toCharArray();
        char[] patternArr = pattern.toCharArray();
        Set<Integer> patternPos = new HashSet<>();

        int textLength = text.length();
        int patternLength = pattern.length();

        for(int i=0; i< textLength - patternLength;  i= i+patternLength) {
            int j = 0;
            while (j < patternLength && textArr[i+j] == patternArr[j]) {
                j++;
            }
            if(j >= patternLength) {
                patternPos.add(i);
            }
        }

        if(patternPos.isEmpty()) {
            System.out.println("No pattern found");
        } else {
            patternPos.forEach(pos -> System.out.println("Pattern position found at : "+pos));
        }
    }
}
