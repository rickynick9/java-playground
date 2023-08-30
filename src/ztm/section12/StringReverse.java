package ztm.section12;

import java.util.Arrays;

public class StringReverse {

    /*
    reverse("Hello")
    (reverse("ello")) + "H"
    ((reverse("llo")) + "e") + "H"
    (((reverse("lo")) + "l") + "e") + "H"
    ((((reverse("o")) + "l") + "l") + "e") + "H"
    (((("o") + "l") + "l") + "e") + "H"
    "olleH"
     */
    private String recusriveReverse(String s) {
        if(s == null || s.length() <= 1)
            return s;
        return recusriveReverse(s.substring(1)) + s.charAt(0);
    }



    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    private void recursiveReverseV1(char[] arr, int startIndex, int endIndex) {
        if(startIndex >= endIndex)
            return;
        swap(arr, startIndex, endIndex);
        recursiveReverseV1(arr, startIndex + 1, endIndex -1);
    }

    private String iterativeReverse(String s) {
        char[] cArray = s.toCharArray();
        int i = 0;
        int j = cArray.length -1;
        while(j > i) {
            char temp = cArray[i];
            cArray[i] = cArray[j];
            cArray[j] = temp;
            i++;
            j--;
        }
        //System.out.println(Arrays.toString(cArray));
        return new String(cArray);
    }

    public static void main(String[] args) {
        String s = "hello";
        StringReverse sRev = new StringReverse();

        System.out.println(sRev.iterativeReverse(s));
        System.out.println(sRev.recusriveReverse(s));

        char[] ch = s.toCharArray();
        sRev.recursiveReverseV1(ch, 0, ch.length -1);
        System.out.println(new String(ch));
        //System.out.println(s.toCharArray());
    }
}
