package ztm.sorting;

import java.util.Arrays;

/*
Array rotation
Input: nums = [1,2,3,4,5,6,7], k = 3
Output: [5,6,7,1,2,3,4]
 */
public class P3 {

    private static int getIndex(int currentIndex, int degreeOfRotation, int arrayLength) {
        //arrayLength here is length - 1
            if(currentIndex + degreeOfRotation > arrayLength) {
                return ((currentIndex + degreeOfRotation) % arrayLength) - 1;
            }
            else if(currentIndex + degreeOfRotation < arrayLength) {
                return (currentIndex + degreeOfRotation) % arrayLength;
            } else {
                return (currentIndex + degreeOfRotation);
            }
    }

    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6,7};
        int degreeOfRotation = 3;
        int arrayLength = arr.length -1;
        int rotatedArray[] = new int[arr.length];

        for(int i=0; i< arr.length; i++) {
            int index = getIndex(i, degreeOfRotation, arrayLength);
            rotatedArray[index] = arr[i];
        }

        Arrays.stream(rotatedArray).forEach(e -> System.out.print(" "+e+" "));
    }
}
