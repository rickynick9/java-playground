package ztm.sorting;

/*

compareTo -> It compares strings on the basis of the Unicode value of each character in the strings.

If the first string is lexicographically greater than the second string, it returns a positive number
(difference of character value). If the first string is less than the second string lexicographically,
it returns a negative number, and if the first string is lexicographically equal to the second string, it returns 0.

Problem Statement : Given a sorted array of strings that is interspersed with empty strings, write a method to find
the location of a given string

input : ball, {"at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""};

input : ball, {"at", "", "", "", "ball", "", "","", "car", "", "", "dad", "", ""};
output : 4

Approach : Binary search is always about sorted array.
We have sorted array but, it is interspersed with empty strings
We always compare input with mid data. Based on comparison we either go to left sub-part or right sub-part.
In case the mid is empty we can attempt to find closest non-empty string and continue our binary search from there.

 */
public class P6 {

    private static int binarySearch(String[] arr, int left, int right, String input) {
        if(left > right)
            return -1;
        int mid = (left + right) / 2;

        if(input.compareTo(arr[mid]) == 0)
            return mid;

        if(input.compareTo(arr[left]) >= 0 && input.compareTo(arr[mid]) < 0) {
            return binarySearch(arr, left, mid -1, input);
        } else if(input.compareTo(arr[mid]) > 0 && input.compareTo(arr[right]) <=0) {
            return binarySearch(arr, mid + 1, right, input);
        }
        return -1;
    }

    private static int binarySearchModified(String[] arr, int left, int right, String input) {
        if(left > right)
            return -1;
        int mid = (left + right) / 2;

        /* If mid is empty then find closest non-empty string */
        if(arr[mid].isEmpty()) {
            int leftE = mid - 1;
            int rightE = mid + 1;
            while(true) {
                if(rightE > right && leftE < left) {
                    return -1;
                } else if (rightE <= right && !arr[rightE].isEmpty()) {
                    mid = rightE;
                    break;
                } else if(leftE >=left && !arr[leftE].isEmpty()) {
                    mid = leftE;
                    break;
                }
                leftE--;
                rightE++;
            }
        }

        if(input.compareTo(arr[mid]) == 0)
            return mid;

        if(input.compareTo(arr[left]) >= 0 && input.compareTo(arr[mid]) < 0) {
            return binarySearchModified(arr, left, mid -1, input);
        } else if(input.compareTo(arr[mid]) > 0 && input.compareTo(arr[right]) <=0) {
            return binarySearchModified(arr, mid + 1, right, input);
        }
        return -1;
    }



    public static void main(String[] args) {
//        String s1 = "This is a good boy";
//        String s2 = "This is a bad boy";
//        System.out.println(s1.compareTo(s2));
//        System.out.println(s2.compareTo(s1));

        String[] arr = {"add", "bad", "bcd", "cat", "dad", "eat"};
        String input = "dad";

        String[] arr1 = {"at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""};
        String input1 = "ball";

        System.out.println(binarySearch(arr, 0, arr.length -1, input));

        System.out.println(binarySearchModified(arr1, 0, arr1.length -1, input1));
    }
}
