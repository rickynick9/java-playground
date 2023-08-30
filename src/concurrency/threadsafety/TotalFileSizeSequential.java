package concurrency.threadsafety;

import java.io.File;

/*
This and other file size programs in this book will take a long time on the first run, and the time will
drop down for subsequent runs performed within minutes. This is because of the caching of the file system.
I have discarded the time from the first run so that all the runs used in comparison have the advantage of cache.
*/
public class TotalFileSizeSequential {


    public long getFileSize(final File file) {
        if (file.isFile()) // we have file.isDirectory() method as well
            return file.length();

        final File[] children = file.listFiles();
        long total = 0;
        if(children != null) { // This condition is for empty directories. Children can be null
            for (final File child : children) {
                total += getFileSize(child);
            }
        }
        return total;
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        final long total = new TotalFileSizeSequential().getFileSize(new File(args[0]));
        long end = System.nanoTime();
        System.out.println("Total Size: " + total);
        System.out.println("Time taken: " + (end - start)/1.0e9);

    }



}
