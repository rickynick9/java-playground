package concurrency.threadsafety.forkjoin;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ArraySum {
    private int sum;

    ArraySum() {
        this.sum = 0;
    }

    private void recursiveSum(int[] arr) {
        if(arr.length < 2) {
            sum +=arr[0];
            return;
        }

        int mid = arr.length / 2;

        int[] left = new int[mid];
        System.arraycopy(arr, 0, left, 0, mid);

        int[] right = new int[arr.length - mid];
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        recursiveSum(left);
        recursiveSum(right);
    }

    public static void main(String[] args) {
        int[] arr = IntStream
                .range(10, 20)
                .toArray();
        Arrays.stream(arr).forEach(i -> System.out.println(i));
        ArraySum arraySum = new ArraySum();
        arraySum.recursiveSum(arr);

        System.out.println("Sum is : "+arraySum.sum);
    }
}
