package concurrency.threadsafety.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/*
Java 7 brings a specialization of ExecutorService with improved efficiency and performance—the fork-join API.
The ForkJoinPool class dynamically manages threads based on the number of available processors and task demand.

Fork-join employs work-stealing where threads pick up (steal) tasks created by other active tasks.
This provides better performance and utilization of threads.

We’d typically use one fork-join pool in an entire application to schedule tasks.
Also, there’s no need to shut down the pool since it employs daemon threads.

To schedule tasks, we provide instances of ForkJoinTask (typically an instance of one of its subclasses) to methods of
ForkJoinPool.
ForkJoinTask allows us to fork tasks and then join upon completion of the task.
ForkJoinTask has two subclasses: RecursiveAction and RecursiveTask.
To schedule tasks that don’t return any results, we use a subclass of RecursiveAction.
For tasks that return results, we use a subclass of RecursiveTask.

The fork-join API expects tasks to have no side effects (don’t change shared state) and no synchronized or blocking methods.
The fork-join API is very useful for problems that can be broken down recursively until small enough to run sequentially.

We ran into the problem of pool-induced deadlock for larger directory hierarchies. Tasks ended up waiting for tasks
they spawned while holding threads much needed for these subtasks to run.

The fork-join API puts an end to that problem with work-stealing.
When a task waits for a subtask to finish, the thread that’s executing the task picks up a new task to run.

We define a static inner class called FileSizeFinder that provides the task execution engine by extending RecursiveTask
and implementing its compute() method. In this method, we total the size of files in a given directory and delegate the
task of finding the size of subdirectories to other tasks, that is, other instances of FileSizeFinder.
The invokeAll() method waits for all the subtasks to finish before moving forward. However, while the task is blocked,
the thread steals more work rather than idling (like the highly responsible members of a successful team).

*/
public class FileSizeForkJoin {
    private final static ForkJoinPool forkJoinPool = new ForkJoinPool();

    private static class FileSizeFinder extends RecursiveTask<Long> {
        final File file;

        public FileSizeFinder(final File theFile) {
            file = theFile;
        }

        @Override
        public Long compute() {
            long size = 0;
            if (file.isFile()) {
                size = file.length();
            } else {
                final File[] children = file.listFiles();
                if (children != null) {
                    List<ForkJoinTask<Long>> tasks = new ArrayList<>();
                    for(final File child : children) {
                        if (child.isFile()) {
                            size += child.length();
                        } else {
                            tasks.add(new FileSizeFinder(child));
                        }
                    }
                    for(final ForkJoinTask<Long> task : invokeAll(tasks)) {
                        size += task.join();
                    }
                }
            }
            return size;
        }
    }

    public static void main(final String[] args) {
        final long start = System.nanoTime();
        final long total = forkJoinPool.invoke(new FileSizeFinder(new File(args[0])));
        final long end = System.nanoTime();
        System.out.println("Total Size: " + total);
        System.out.println("Time taken: " + (end - start)/1.0e9); }
}
