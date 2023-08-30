package concurrency.threadsafety.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/*
Ref : https://hackernoon.com/parallel-merge-sort-with-forkjoin-framework
https://hackernoon.com/u/alexandermakeev


The fibonacci series numbers are given as: 0, 1, 1, 2, 3, 5, 8, 13, 21, 38, . . .
In a Fibonacci series, every term is the sum of the preceding two terms, starting from 0 and 1 as the first and second terms.

Fn = Fn-1 + Fn-2.
Fn = 0, n =0
Fn = 1, n =1
Fn = Fn-1 + Fn-2 , n> 1
 */
public class Fibonacci {

    private final static ForkJoinPool forkJoinPool = new ForkJoinPool();

    class FibonacciTask extends RecursiveTask<Integer> {


        @Override
        protected Integer compute() {
            return null;
        }
    }

}
