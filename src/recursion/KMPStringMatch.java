package recursion;

import java.util.Arrays;
import java.util.stream.Stream;

public class KMPStringMatch {

    static void computeLPSArray(String pat, int M, int lps[]) {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment
                    // i here
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++;
                }
            }

        }
    }

    public static void main(String[] args) {
        String pattern = "abcdecbag";

        int M = pattern.length();

        int lps[] = new int[M];
        Arrays.fill(lps, 0);



        for(int i =0; i< lps.length; i++) {
            System.out.println(lps[i]);
        }
        System.out.println("\n");


        int j = 0; // index for pat[]

        // Preprocess the pattern (calculate lps[]
        // array)
        computeLPSArray(pattern, M, lps);


        for(int i =0; i< lps.length; i++) {
            System.out.println(lps[i]);
        }
    }

}
