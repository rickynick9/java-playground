package concurrency.threadsafety.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergeSortConcurrent {

    public void sort() {

        int[] array = IntStream
                .range(0, 100_000_000)
                .map(i -> ThreadLocalRandom.current().nextInt())
                .toArray();

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        //System.out.println("Unsorted Array");
        //Arrays.stream(array).forEach(i -> System.out.println(i));

        long start = System.nanoTime();
        MergeSortTask mergeSortTask = new MergeSortTask(array);
        forkJoinPool.invoke(mergeSortTask);

        //System.out.println("Sorted Array");
        //Arrays.stream(mergeSortTask.getArr()).forEach(i -> System.out.println(i));
        assertTrue(isSorted(mergeSortTask.getArr()));
        long end = System.nanoTime();

        System.out.println("Time taken to sort array : "+(end - start)/1.0e9);
    }

    class MergeSortTask extends RecursiveAction {

        private final int[] arr;

        public int[] getArr() {
            return arr;
        }

        MergeSortTask(int[] arr) {
            this.arr = arr;
        }

        @Override
        public void compute() {

            if (arr.length < 2) return;
            int mid = arr.length / 2;

            int[] left = new int[mid];
            System.arraycopy(arr, 0, left, 0, mid);

            int[] right = new int[arr.length - mid];
            System.arraycopy(arr, mid, right, 0, arr.length - mid);

            invokeAll(new MergeSortTask(left), new MergeSortTask(right));
            merge(left, right);
        }

        private void merge(int[] left, int[] right) {
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
    }


    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1])
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        MergeSortConcurrent mergeSortConcurrent = new MergeSortConcurrent();
        mergeSortConcurrent.sort();
    }
}
