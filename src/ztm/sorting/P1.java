package ztm.sorting;

import java.util.Arrays;

/*
You are given 2 sorted arrays A and B, where A has large enough buffer at the end to hold B. Write a method
to merge B into A in sorted order.

A = [2, 4, 6, 8, 10]
B = [1, 3, 5, 7, 9, 11]
 */
public class P1 {


    public static void mergeSortedArray(int[] A, int[] B) {
        //int aIndex = A.length - 1; //It cannot be length -1 because array A is empty.
        //We might need to traverse Array A to find last index.
        int aIndex = 0;
        while(A[aIndex] != 0)
            aIndex++;

        aIndex = aIndex - 1; //index starts from 0;

        int bIndex = B.length - 1;
        int mergedIndex = aIndex + bIndex + 1;

//        System.out.println("aIndex :"+aIndex);
//        System.out.println("bIndex :"+bIndex);
//        System.out.println("mergedIndex :"+mergedIndex);

        while (bIndex >=0) {
            if(aIndex >=0 && A[aIndex] > B[bIndex]) {
                A[mergedIndex] = A[aIndex];
                aIndex--;
                mergedIndex--;
            } else {
                A[mergedIndex] = B[bIndex];
                bIndex--;
                mergedIndex--;
            }
        }
    }

    public static void main(String[] args) {
        int[] A = new int[11];
        A[0] = 2; A[1] = 4; A[2] = 6; A[3] = 8; A[4] = 10;

        int[] B = new int[6];
        B[0] = 1; B[1] = 3; B[2] = 5; B[3] = 7; B[4] = 9; B[5] = 11;

        Arrays.stream(A).forEach( e -> System.out.print(" "+e+ " "));

        mergeSortedArray(A, B);
        System.out.println("\n");

        Arrays.stream(A).forEach( e -> System.out.print(" "+e+ " "));

    }
}
