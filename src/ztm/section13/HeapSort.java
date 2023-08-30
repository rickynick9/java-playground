package ztm.section13;

import java.util.Arrays;

public class HeapSort {

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static void heapify(int[] arr, int i) {
        //start with some
        int N = arr.length;
        int largest = i;
        //System.out.println("largest index is :"+largest);

        if(2 * i + 1 < N && arr[2*i + 1] > arr[i]) {
            largest = 2*i + 1;
        }

        if(2 * i + 2 < N && arr[2*i + 2] > arr[largest]) {
            largest = 2*i + 2;
        }

        if(largest != i) {
            swap(arr, i, largest);
            heapify(arr, largest);

        }
    }

    private static void buildHeap(int arr[])
    {
        // Index of last non-leaf node
        int startIdx = (arr.length / 2) -1;
        System.out.println("start index :"+startIdx);

        // Perform reverse level order traversal
        // from last non-leaf node and heapify
        // each node
        for (int i = startIdx; i >= 0; i--) {
            heapify(arr, i);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 5, 4, 6, 13, 10, 9, 8, 15, 17};
        buildHeap(arr);
        Arrays.stream(arr).forEach(e -> System.out.println(" "+e+" "));
    }
}
