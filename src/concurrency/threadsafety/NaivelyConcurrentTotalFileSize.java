package concurrency.threadsafety;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/*
We can divide the problem into tasks where each task accepts a directory and returns its total size.
The Callable interface is a good fit for this because its call() method can return a result when done.

We got this exception : Exception in thread "main" java.util.concurrent.TimeoutException
The flaw is in the getFileSize() method; it clogs the thread pool.
As this method discovers subdirectories, it schedules the task of exploring them to other threads.
Once it schedules all these tasks, this method awaits response from each one of them.

If we had only a few directories, then it’s no big deal. But if we have a deep hierarchy, this method will get stuck.
While threads wait for response from the tasks they create, these tasks end up waiting in the ExecutorService’s queue
for their turn to run. This is a potential “pool induced deadlock,” if we didn’t have the timeout. Since we used a
timeout, we’re able to at least terminate unfavorably rather than wait forever.

We have created a thread hierarchy here
Lets say there are 5 subdirectories inside main directory - A, B, C, D and E
Parent thread is waiting for all the below threads to complete
Similarly all threads are waiting for some other thread to complete directory exploration.
A - Thread created to explore subsdirectory AA -> Another thread created to explore subdirectory -AAA and so on
B - Thread created to explore subsdirectory BB -> Another thread created to explore subdirectory -BBB and so on
C - Thread created to explore subsdirectory CC -> Another thread created to explore subdirectory -CCC and so on
D - Thread created to explore subsdirectory DD -> Another thread created to explore subdirectory -DDD and so on
E - Thread created to explore subsdirectory EE -> Another thread created to explore subdirectory -EEE and so on

Solution :
We want to delegate the computation of size for various directories to different threads but NOT HOLD ON to the
CALLING THREAD while we wait for these tasks/threads to respond.

One way to tackle this is for each task to return a list of subdirectories it finds, instead of the full size for
a given directory.

Then from the main task we can dispatch other tasks to navigate the subdirectories. This will prevent
HOLDING THREADS for any period longer than simply fetching the immediate subdirectories.




 */
public class NaivelyConcurrentTotalFileSize {

    public static long getFileSize(final File file, final ExecutorService executorService) throws ExecutionException, InterruptedException, TimeoutException {
        if(file.isFile())
            return file.length();

        final File[] children = file.listFiles();
        long total =0;
        if(children != null) {
            final List<Future<Long>> partialTotalFutures = new ArrayList<>();
            for (File child : children) {
                partialTotalFutures.add(executorService.submit(() -> getFileSize(child, executorService)));
            }

            for (final Future<Long> partialTotalFuture : partialTotalFutures)
                total += partialTotalFuture.get(100, TimeUnit.SECONDS);
        }
        return total;
    }

    private long getTotalSizeOfFile(final String fileName) throws ExecutionException, InterruptedException, TimeoutException {
        final ExecutorService service = Executors.newFixedThreadPool(100);
        try {
            return getFileSize(new File(fileName), service);
        } finally {
            service.shutdown();
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        final long start = System.nanoTime();
        final long total = new NaivelyConcurrentTotalFileSize().getTotalSizeOfFile(args[0]);
        final long end = System.nanoTime(); System.out.println("Total Size: " + total);
        System.out.println("Time taken: " + (end - start)/1.0e9);
    }
}
