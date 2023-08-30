package ztm.section13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
https://levelup.gitconnected.com/bucket-sort-visualize-design-and-analyse-updated-2023-f0f2ba866d55

Bucket Sort is used when input numbers are uniformly distributed over a range.

Problem : Sort a large set of floating-point numbers that are in
the range from 0.0 to 1.0 and are uniformly distributed across the range.
Let’s say the numbers are {0.42,0.32,0.23,0.52,0.25,0.47,0.51}.

We may use any Comparison-based sorting algorithm. But the lower bound for the Comparison-based sorting algorithm
is Ω(n log n) (Merge sort, Heap sort, Quick-sort, etc). These algorithms cannot do better than Ω(n log n).

We may use Counting sort to sort these elements in linear time but Counting sort can not be applied here as we use keys
as the index in counting sort and in this case, keys are floating-point numbers.

Let’s take the input element 0.32. It is multiplied by the size of the new array is 0.32*10 = 3.2 => and converts to 3
as new array indices range from 0–9 only and these indices are integers. Finally, .32 is inserted into bucket 3.

bucketSort(arr[], n)
    1. Create 'n' empty buckets using some data structure.
    2. For each array element arr[i].
         2.1 Insert arr[i] into bucket[n*array[i]]
    3. Sort individual buckets using insertion sort.
    4. Now concatenate all the sorted buckets and create single array.

 */
public class BucketSort {

    public static double[] bucketSort(double[] arr) {
        int bucketSize = 10;
        int k =0;
        ArrayList<Double>[] bucket = new ArrayList[bucketSize];
        double outputArr[] = new double[arr.length];

        //Create 'n' empty buckets
        for (int i = 0; i < bucketSize; i++)
            bucket[i] = new ArrayList<>();

        //insert into bucket
        for(int i=0; i< arr.length; i++) {
            int index = (int) arr[i] * bucketSize;
            bucket[index].add(arr[i]);
        }

        //Sort the elements of each bucket one by one
        for (int i = 0; i <bucketSize; i++) {
            Collections.sort((bucket[i]));
        }

        for(int i=0; i<bucketSize; i++) {
            for(int j=0; j<bucket[i].size(); j++) {
                outputArr[k++] = bucket[i].get(j);
            }
        }

        return outputArr;
    }


    public static void main(String[] args) {
        double[] arr = {0.42, 0.32, 0.33, 0.52, 0.37, 0.47, 0.51 };
        Arrays.stream(bucketSort(arr)).forEach(e -> System.out.print(" "+e+" "));
    }
}
