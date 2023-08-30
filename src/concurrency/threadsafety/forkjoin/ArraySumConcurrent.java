package concurrency.threadsafety.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ArraySumConcurrent {

    final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

    class ArraySumTask extends RecursiveTask<Integer> {

        final int[] arr;
        //int sum;

        ArraySumTask(int[] arr) {
            this.arr = arr;
            //this.sum = 0;
        }
        @Override
        protected Integer compute() {
            int sum = 0;
            List<ForkJoinTask<Integer>> tasks = new ArrayList<>();
            if (arr.length < 2) {
                sum += arr[0];
                return sum;
            }
            int mid = arr.length / 2;
            int[] left = new int[mid];
            System.arraycopy(arr, 0, left, 0, mid);

            int[] right = new int[arr.length - mid];
            System.arraycopy(arr, mid, right, 0, arr.length - mid);

            tasks.add(new ArraySumTask(left));
            tasks.add(new ArraySumTask(right));

            //invokeAll(new ArraySumTask(left), new ArraySumTask(right));

            for(final ForkJoinTask<Integer> task : invokeAll(tasks)) {
                sum += task.join();
            }

            return sum;
        }
    }

    public int concurrentSum(int[] arr) {
        return forkJoinPool.invoke(new ArraySumTask(arr));
    }

    public static void main(String[] args) {
        int[] arr = IntStream
                .range(10, 20)
                .toArray();
        Arrays.stream(arr).forEach(i -> System.out.println(i));
        ArraySumConcurrent arraySumConcurrent = new ArraySumConcurrent();
        int sum = arraySumConcurrent.concurrentSum(arr);
        System.out.println("Sum is : "+sum);
    }
}
