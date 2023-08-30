package ztm.section13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortClass {

    /*
    First pass : The largest element is placed in its correct position, i.e., the end of the array.
    Second pass : Place the second largest element at correct position

     */
    private static int[] bubbleSort(int[] arr) {
        for(int i=0; i<arr.length; i++) {
            for(int j=0; j<arr.length-1; j++) {
                if(arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
  }

  /*
  find the smallest item and swap it with the first element and so on
   */
  private static int[] selectionSort(int[] arr) {
      int length = arr.length ;
        for(int i=0; i<length; i++) {
            int min = arr[i];
            int minIndex = i;
            for(int j=i; j<length; j++) {
                if(arr[j] < min) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            swap(arr, i, minIndex);
        }
        return arr;
  }

  /*
  Say you are given 10 cards, 1 to 10 of spades, all shuffled, and you want to sort these cards.

You would basically pick any random card(e.g. 7), and place it into your left hand, assuming the left hand is meant to
carry the sorted cards.
Then you would pick another random card, say 2, and place 2 in the correct position on your left hand, i.e. before 7.
Then again if you pick 5, you would place it between 2 and 7 on your left hand, and this way we know we are able to
sort our deck of cards.

You insert one element at a time at correct position.
   */
  private static int[] insertionSort(int[] arr) {

      for(int i=1; i< arr.length; i++) {
          int currentElement = arr[i];
          //compare this current elements with all previous elements.
          // insert this element at appropriate position
          int j = i-1;
          while(j >= 0 && arr[j] > currentElement) {
              arr[j+1] = arr[j]; // we are moving big elements to right
              j--;
          }
          //we have move all elements and found correct position. insert current element at this position.
          arr[j + 1] = currentElement;
      }

      return arr;
  }

  private static void swap(int[] arr, int i, int j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
  }

  /*
  Merge Sort
   */

    private static int[] mergeSort(int[] arr) {
        if (arr.length < 2) return arr;

        int mid = arr.length / 2;
        int[] left = new int[mid];
        System.arraycopy(arr, 0, left, 0, mid);

        int[] right = new int[arr.length - mid];
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
        return arr;
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j])
                arr[k++] = left[i++];
            else
                arr[k++] = right[j++];
        }

        while (i < left.length) {
            arr[k++] = left[i++];
        }

        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }

    /*
    Quick sort : Arrange array into sub-arrays around pivot element.
    All elements less that pivot is moved to left sub-array
    All elements greater than pivot is moved to right sub-array
    We do this recursively and return
    less + pivot + right
     */

    private static int[] quickSort(int[] arr) {
        int[] less = new int[arr.length];
        int[] more = new int[arr.length];
        int[] pivotList = new int[arr.length];

        if(arr.length <= 1)
            return arr;

        int i = 0; int j = 0; int k = 0;
        int pivot = arr[0];

        for (int value : arr) {
            if (value < pivot)
                less[i++] = value;
            else if (value > pivot)
                more[j++] = value;
            else
                pivotList[k++] = value;
        }

        System.out.println("Before less");
        less = quickSort(less);
        System.out.println("After less");
        more = quickSort(more);
        return mergeArrays(less, pivotList, more);
    }

    final static public int[] mergeArrays(final int[] ...arrays ) {
        int size = 0;
        for ( int[] a: arrays )
            size += a.length;

        int[] res = new int[size];

        int destPos = 0;
        for ( int i = 0; i < arrays.length; i++ ) {
            if ( i > 0 ) destPos += arrays[i-1].length;
            int length = arrays[i].length;
            System.arraycopy(arrays[i], 0, res, destPos, length);
        }

        return res;
    }


    /*
    This is simple but we can check this as well : https://www.scaler.com/topics/quick-sort-in-java/
     */
    public static List<Integer> quickSortV1(List<Integer> arr) {
        List<Integer> less = new ArrayList<>();
        List<Integer> pivotList = new ArrayList<>();
        List<Integer> more = new ArrayList<>();
        if (arr.size() <= 1) {
            return arr;
        } else {
            int pivot = arr.get(0);
            for (int i : arr) {
                if (i < pivot) {
                    less.add(i);
                } else if (i > pivot) {
                    more.add(i);
                } else {
                    pivotList.add(i);
                }
            }
            less = quickSortV1(less);
            more = quickSortV1(more);
            List<Integer> result = new ArrayList<>();
            result.addAll(less);
            result.addAll(pivotList);
            result.addAll(more);
            return result;
        }
    }


    private static int partition(int[] arr, int start, int end) {
        int pivot = arr[end];
        int pi = start;
        for(int j = start; j<=end; j++) {
            if(arr[j] < pivot) {
                if(pi != j)
                    swap(arr, pi, j);
                pi++;
            }
        }
        swap(arr, pi, end);
        return pi;
    }

    private static int[] quickSortV2(int[] arr, int start, int end) {
        if (start < end) {
            // The partitioning index is represented by pi.

            int pi = partition(arr, start, end);

            // Separately sort elements before
            // partition and after partition
            quickSortV2(arr, start, pi - 1);
            quickSortV2(arr, pi + 1, end);

        }
        return arr;
    }


    public static void main(String[] args) {
        //int[] arr = {99, 44, 6, 2, 1, 5, 63, 87, 283, 4, 0};
        int[] arr = {99, 44, 6, 2, 1, 5, 63, 87, 283};
        //System.out.println(bubbleSort(arr));
        //Arrays.stream(bubbleSort(arr)).forEach(e -> System.out.print(" "+e+ " "));

       Arrays.stream(arr).forEach(e -> System.out.print(" "+e+ " "));

        System.out.println("\n");

       //Arrays.stream(mergeSort(arr)).forEach(e -> System.out.print(" "+e+ " "));

        Arrays.stream(quickSortV2(arr, 0, (arr.length -1))).forEach(e -> System.out.print(" "+e+ " "));

       //quickSortV1(Arrays.stream(arr).boxed().toList()).forEach(e -> System.out.print(" "+e+ " "));


    }

    /*
    Use cases
    eBay sorts listing by current bid amount
    I will probably use Radix or couting sort. In Radix and counting sort there is no comparison. It is rather based
    on binary numbers

    Sort sports scrore on ESPN

     */
}
