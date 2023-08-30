package concurrency.threadsafety.forkjoin;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
Created in 1945, John Von Neumann developed the merge sort algorithm using the divide-and-conquer approach.

Divide and conquer algorithms decompose complex problems into smaller sub-parts, where a defined solution is applied
recursively to each sub-part.
Each sub-part is then solved separately, and the solutions are recombined to solve the original problem.

3 primary elements of divide and conquer

1. Decomposition of the larger problem into smaller subproblems. (Divide)
2. Recursive utilization of functions to solve each of the smaller subproblems. (Conquer)
3. The final solution is a composition of the solution to the smaller subproblems of the larger problem. (Combine)

https://www.khanacademy.org/computing/computer-science/algorithms/merge-sort/a/overview-of-merge-sort

A merge sort algorithm splits an unsorted algorithm into 2 halves, it sorts the 2 halves and then merge them
together to form a sorted collection.

The basic idea of merge sort is this, it tends to be a lot easier to sort two smaller list than the large one.

Conceptually, merge sort asserts that instead of a one unsorted list, it’s a lot easier to sort and join together
two sorted lists. The idea is that if we could somehow magically end up with two sorted halves, then we could very
easily merge those two sorted sublists together.

For starters, the fact that a list with a single item is, by definition, a “sorted” list is what makes it easy for
us to know when we’ve hit our smallest possible “subproblem”. When this algorithm is implemented in code, this ends
up being the base case for determining when the recursion should end.

When we can’t break down our list into any smaller possible parts, and when we only have one item that is sorted,
we’ve hit our recursive base case.

Another reason that divide and conquer works here is once we know how to merge two items together and have figured
out the logic behind that, we basically can just keep reusing that same logic and continue applying it to every
built-up sublist that we merge together.

This is exactly what makes recursion so powerful. Once we have figured out how to solve the problem of merging
two lists together, it does not matter if list has one element or 100 elements. We can re-use the same logic.


The poor performance of Random in a multi-threaded environment is due to contention – given that multiple threads share
the same Random instance.

To address that limitation, Java introduced the java.util.concurrent.ThreadLocalRandom class in JDK 7 – for generating
random numbers in a multi-threaded environment.
 */
public class MergeSort {

    // System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length):
    private static void mergeSort(int[] arr) {
        if (arr.length < 2) return;

        int mid = arr.length / 2;
        int[] left = new int[mid];
        System.arraycopy(arr, 0, left, 0, mid);

        int[] right = new int[arr.length - mid];
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        mergeSort(left);
        mergeSort(right);
        merge(arr, left, right);
    }

    public void sort(int[] arr) {
        if (arr.length < 2) return;
        int mid = arr.length / 2;

        int[] left = new int[mid];
        System.arraycopy(arr, 0, left, 0, mid);

        int[] right = new int[arr.length - mid];
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        sort(left);
        sort(right);
        merge(arr, left, right);
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

    public static void main(String[] args) {
        int[] arr = IntStream
                .range(0, 100_000_000)
                .map(i -> ThreadLocalRandom.current().nextInt())
                .toArray();

        long start = System.nanoTime();
        mergeSort(arr);
        assertTrue(isSorted(arr));
        long end = System.nanoTime();

        System.out.println("Time taken to sort array : "+(end - start)/1.0e9);
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1])
                return false;
        }
        return true;
    }

}
