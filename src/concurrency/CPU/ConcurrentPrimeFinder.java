package concurrency.CPU;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/*
Since this is a computationally intensive task, throwing a lot of threads on the problem will not help.
The blocking coefficient is 0, and so the suggested number of threads is equal to the number of cores.

Number of primes under 10000000 is 664579
Time (seconds) taken is 1.835901977

The execution time was not reduced to 1/8th. What went wrong ?

This is computationally intensive, we already know that increasing the threads to more than the number
of cores will not help. So, it’s gotta be the number of parts. Let’s try to justify that and arrive at a solution.

Workload across the 8 threads/cores is not evenly distributed—the first one finished much faster than the last.
If we think about the nature of the given problem, it now becomes obvious—the effort to check a larger prime in
the second part is more than the effort to check a smaller prime in the first part. Effort to check a large prime in 8th
part is even more.

The effort to check a larger prime in the 8th part is more than the effort to check a smaller prime in the first part.
To achieve the maximum speedup, we need to partition the problem across the 8 threads evenly.

In general, a fair distribution of workload among the parts may not be easy to achieve; it requires a fairly good
understanding of the problem and its behavior across the range of input. For the count primes problem, the approach
we took was to split the range into equal parts in serial order. Instead, we can try to group them into combinations
of big and small numbers to get a uniform distribution.

The code we currently have does not lend itself to that kind of partitioning. If we change the code to do that, we will
notice that the runtime reduces. The split, however, gets harder.

Fortunately, there is a simpler solution.
The main problem with fewer parts, like two, is that one core does more work while the other cores twiddle their digit.
The finer we divide the problem, more likely there will be enough slices to keep all the cores busy.
Start with more parts than threads, and then as threads complete smaller parts, they will pick up other parts to execute.
Some cores can work on long-running tasks, while other cores can pick up several short-running tasks.

When threads don’t compete to access mutable data, there’s no issue of visibility and crossing the memory barrier.
We also don’t have to worry about controlling the execution sequence of threads; since they don’t compete, there are
no mutually exclusive sections to safeguard in code.

Provide shared immutability where possible. Otherwise, follow isolated mutability—ensure only one thread ever can access
that mutable variable.

Uniform partitioning
Although the number of threads affects performance, it is not the only thing. The workload of each part and how much
time each part takes to complete relative to others both affect performance. A uniform partitioning of the problem may
take too much effort and may not yield better results than ad hoc partitioning. Weigh the efforts vs. the benefit.
We should try to arrive at a fair workload by using a simple approach to partitioning. In any case, good partitioning
requires understanding the nature of the problem and its behavior for different input.

*/
public class ConcurrentPrimeFinder extends AbstractPrimeFinder {

    private final int poolSize;
    private final int numberOfParts;

    public ConcurrentPrimeFinder(final int thePoolSize, final int theNumberOfParts) {
        poolSize = thePoolSize;
        numberOfParts = theNumberOfParts;
    }

    public int countPrimes(final int number) {
        int count = 0;
        try {
            final List<Callable<Integer>> partitions = new ArrayList<>();
            final int chunksPerPartition = number / numberOfParts;

            // Task addition to ArrayList
            for (int i = 0; i < numberOfParts; i++) {
                final int lower = (i * chunksPerPartition) + 1;
                final int upper = (i == numberOfParts - 1) ? number : lower + chunksPerPartition - 1;
                partitions.add(() -> countPrimesInRange(lower, upper));
            }

            ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
            List<Future<Integer>> resultFromParts = executorPool.invokeAll(partitions, 10000, TimeUnit.SECONDS);

            executorPool.shutdown();

            for (final Future<Integer> result : resultFromParts)
                count += result.get();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return count;
    }

    public static void main(final String[] args) throws InterruptedException {
        if (args.length < 3)
            System.out.println("Usage: number poolsize numberOfParts");
        else
            new ConcurrentPrimeFinder(
                    Integer.parseInt(args[1]), Integer.parseInt(args[2]))
                    .timeAndCompute(Integer.parseInt(args[0]));
    }
}
