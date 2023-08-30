package concurrency.threadsafety;


import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/*
Future served in two ways in the previous example. First it helped get the re-sult of the tasks.
Implicitly, it also helped coordinate the thread with these tasks/threads.
It allowed us to wait for those results to arrive before the thread proceeded with its work.
Future, however, is not helpful as a coordination tool if tasks have no results to return.
We don’t want to have an artificial return result simply for the sake of coordination.
CountDownLatch can serve as a coordination tool in situations like this.


The problem with NaivelyConcurrentTotalFileSize is that each thread waited for the tasks it’s scheduled to complete.
The nice thing about both the versions of code was they had no mutable shared state.
If we compromise a little on the shared mutability, we can keep the code simple and make it work as well.

Instead of returning the subdirectories and the file size, we can let each thread update a shared variable.
We still have to ensure that the main thread waits for all the subdirectories to be visited.
We can use the CountDownLatch for this, to signal the end of wait.
The latch works as a synchronization point for one or more threads to wait for other threads to reach a point of completion.

We’ll recursively delegate the task of exploring subdirectories to different threads.
When a thread discovers a file, instead of returning the results, it updates a shared variable totalSize of type AtomicLong.
AtomicLong provides thread- safe methods to modify and retrieve the values of a simple long variable.

In addition, we’ll use another AtomicLong variable called pendingFileVisits to keep a tab on the number of files still
to be visited. When this count goes to zero, we release the latch by calling countDown().

*/
public class ConcurrentTotalFileSizeWLatch {

    final private AtomicLong pendingFileVisits = new AtomicLong();
    final private AtomicLong totalSize = new AtomicLong();
    final private CountDownLatch latch = new CountDownLatch(1);

    private ExecutorService service;

    private AtomicLong totalShared = new AtomicLong();
    private void getFileSize(final File file) {
        long total = 0;
        if(file.isFile())
            total = file.length();
        else {
            File[] children = file.listFiles();
            for (File child : children) {
                if (child.isFile()) {
                    total += child.length();
                } else if (child.isDirectory()) {
                    pendingFileVisits.incrementAndGet();
                    service.execute(() -> getFileSize(child));
                }
            }
        }
        totalShared.addAndGet(total);
        if(pendingFileVisits.decrementAndGet() == 0)
            latch.countDown();
    }
    private void updateTotalSizeOfFilesInDir(final File file) {
        long fileSize = 0;
        if (file.isFile())
            fileSize = file.length();
        else {
            final File[] children = file.listFiles();
            if (children != null) {
                for(final File child : children) {
                    if (child.isFile())
                        fileSize += child.length();
                    else {
                        pendingFileVisits.incrementAndGet();
                        service.execute(() -> updateTotalSizeOfFilesInDir(child));
                    }
                }
            }
        }
        totalSize.addAndGet(fileSize);
        if(pendingFileVisits.decrementAndGet() == 0)
            latch.countDown();
    }

    private long getTotalSizeOfFile(final String fileName) throws InterruptedException {
        service = Executors.newFixedThreadPool(100);
        pendingFileVisits.incrementAndGet();
        try {
            updateTotalSizeOfFilesInDir(new File(fileName));
            latch.await(100, TimeUnit.SECONDS);
            return totalSize.longValue();
        } finally {
            service.shutdown();
        }
    }

    public static void main(final String[] args) throws InterruptedException {
        final long start = System.nanoTime();
        final long total = new ConcurrentTotalFileSizeWLatch().getTotalSizeOfFile(args[0]);
        final long end = System.nanoTime();
        System.out.println("Total Size: " + total);
        System.out.println("Time taken: " + (end - start)/1.0e9);
    }

    /*
    Math riddle
    https://mindyourdecisions.com/blog/2017/06/18/the-seemingly-impossible-guess-the-number-logic-puzzle/
    https://www.rd.com/list/math-riddles/
    https://www.hitbullseye.com/puzzle/mathematical-puzzles.php


     */


}
