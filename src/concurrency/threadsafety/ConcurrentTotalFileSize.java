package concurrency.threadsafety;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/*
This version of concurrent implementation took quite some effort, but when compared to the old
NaivelyConcurrentTotalFileSize, the new ConcurrentTotalFileSize is a better design—it does not hold a thread for a long time.

We dispatched tasks to threads and then waited for their results only in the main thread.
All the other threads are quick, in that they only take time to find the total size of files and, in addition,
return the list of subdirectories in a given directory.

 */
public class ConcurrentTotalFileSize {

    class SubDirectoriesAndSize {
        final public long size;
        final public List<File> subDirectories;

        public SubDirectoriesAndSize(final long totalSize, final List<File> theSubDirs) {
            size = totalSize;
            subDirectories = Collections.unmodifiableList(theSubDirs);
        }
    }

    private SubDirectoriesAndSize getTotalAndSubDirs(final File file) {
        long total = 0;
        final List<File> subDirectories = new ArrayList<>();
        File[] children = file.listFiles();

        if(children !=null) {
            for (File child : children) {
                if (child.isDirectory())
                    subDirectories.add(child);
                else if (child.isFile())
                    total += child.length();
            }
        }
        return new SubDirectoriesAndSize(total, subDirectories);
    }


    private long getTotalSizeOfFilesInDir(final File file) throws InterruptedException, ExecutionException, TimeoutException {

        final ExecutorService service = Executors.newFixedThreadPool(100);
        try {
            long total = 0;
            final List<File> directories = new ArrayList<>();
            directories.add(file);
            while(!directories.isEmpty()) {
                final List<Future<SubDirectoriesAndSize>> partialResults = new ArrayList<>();
                for(final File directory : directories) {
                    partialResults.add(service.submit(() -> getTotalAndSubDirs(directory)));
                }
                directories.clear();
                for(final Future<SubDirectoriesAndSize> partialResultFuture : partialResults) {
                    final SubDirectoriesAndSize subDirectoriesAndSize = partialResultFuture.get(100, TimeUnit.SECONDS);
                    directories.addAll(subDirectoriesAndSize.subDirectories);
                    total += subDirectoriesAndSize.size;
                }
            }
            return total;
        } finally {
            service.shutdown();
        }
    }

    public static void main(final String[] args)
            throws InterruptedException, ExecutionException, TimeoutException {
        final long start = System.nanoTime();
        final long total = new ConcurrentTotalFileSize().getTotalSizeOfFilesInDir(new File(args[0]));
        final long end = System.nanoTime();
        System.out.println("Total Size: " + total);
        System.out.println("Time taken: " + (end - start)/1.0e9);
    }



}
