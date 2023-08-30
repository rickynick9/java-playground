package ztm.BigO;

import java.util.Arrays;

/*
Log all pairs of an array
 */
public class Example2 {

    private static final int[] arr = {1,2,3,4,5};

    public static void main(String[] args) {
        printAllNumbersThenPairSums(arr);
//        for (int i=0; i< arr.length; i++) {
//            for(int j = 0; j< arr.length; j++) {
//                if(i != j)
//                    System.out.println(arr[i] + " , "+arr[j]);
//            }
//        }
    }

    public static void printAllNumbersThenPairSums(int[] arr) {
        Arrays.stream(arr).forEach(number -> {
            System.out.println(number);
        });

        Arrays.stream(arr).forEach(firstNumber -> {
            Arrays.stream(arr).forEach(secondNumber -> {
                int sum = firstNumber + secondNumber;
                System.out.println(""+firstNumber+"" + "+" + ""+secondNumber+"" + " = "+sum);
            });
        });
    }
}
