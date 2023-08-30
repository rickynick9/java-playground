package concurrency.CPU;

/*

We can’t run a single-threaded application on a multicore processor and
expect better results. We have to divide it and run multiple tasks concurrently. But, programs don’t divide
the same way and benefit from the same number of threads.

I have worked on scientific applications that are computation intensive and
also on business applications that are IO intensive because they involve file,
database, and web service calls. The nature of these two types of applications
is different and so are the ways to make them concurrent.

We’ll work with two types of applications in this chapter. The first one is an
IO-intensive application that will compute the net asset value for a wealthy
user. The second one will compute the total number of primes within a range
of numbers—a rather simple but quite useful example of a concurrent
computation–intensive program.

how many threads to create ?
how to divide the problem ?
how much speedup to expect ?

For a large problem, we may create as many parts as we like, but we can’t create too many threads because we have
limited resources.

================= Determining the Number of Threads ========================================
For a large problem, we’d want to have at least as many threads as the number of available cores.
This will ensure that as many cores as available to the process are put to work to solve our problem. We can easily find the
number of available cores; all we need is a simple call from the code:
Runtime.getRuntime().availableProcessors();
So, the minimum number of threads is equal to the number of available cores.

If all tasks are computation intensive, then this is all we need. Having more threads will actually hurt in this case
because cores would be context switching between threads when there is still work to do. If tasks are IO intensive,
then we should have more threads.

When a task performs an IO operation, its thread gets blocked. The processor immediately context switches to run other
eligible threads. If we had only as many threads as the number of available cores, even though we have tasks to perform,
they can’t run because we haven’t scheduled them on threads for the processors to pick up. If tasks spend 50 percent of
the time being blocked, then the number of threads should be twice the number of available cores.

Number of threads = Number of Available Cores / (1 - Blocking Coefficient)
where the blocking coefficient is between 0 and 1.

A computation-intensive task has a blocking coefficient of 0, whereas an IO-intensive task has a value close to
1—a fully blocked task is doomed, so we don’t have to worry about the value reaching 1.

To determine the number of threads, we need to know two things:
• The number of available cores
• The blocking coefficient of tasks
The first one is easy to determine; we can look up that information, even at runtime, as we saw earlier. It takes a bit
of effort to determine the blocking coefficient. We can try to guess it, or we can use profiling tools or the
java.lang.management API to determine the amount of time a thread spends on system/IO operations vs. on CPU-intensive tasks.


============================= Determining the Number of Parts ====================================
first thought, we could have as many parts as the number of threads. That’s a good start but not adequate; we’ve ignored the
nature of the problem being solved.

In the net asset value application, the effort to fetch the price for each stock is the same. So, dividing the total
number of stocks into as many groups as the number of threads should be enough.

However, in the primes application, the effort to determine whether a number is prime is not the same for all numbers.
Even numbers fizzle out rather quickly, and larger primes take more time than smaller primes. Taking the range of numbers
and slicing them into as many groups as the number of threads would not help us get good performance. Some tasks would finish
faster than others and poorly utilize the cores.

In other words, we’d want the parts to have even work distribution. We could spend a lot of time and effort to divide
the problem so the parts have a fair distribution of load.

However, there would be two problems. First, this would be hard; it would take a lot of effort and time.
Second, the code to divide the problem into equal parts and distribute it across the threads would be complex.

It turns out that keeping the cores busy on the problem is more beneficial than even distribution of load across parts.
When there’s work left to be done, we need to ensure no available core is left to idle, from the process point of view.
So, rather than splitting hairs over an even distribution of load across parts, we can achieve this by creating far more
parts than the number of threads. Set the number of parts large enough so there’s enough work for all the available cores
to perform on the program.



The number of cores has a greater influence on the speedup of computation intensive applications than on IO-bound
applications, as we’ll see in this section.

The example we’ll use is very simple; however, it has a hidden surprise—the uneven workload will affect the speedup.

Let’s write a program to compute the number of primes between 1 and 10
million. Let’s first solve this sequentially, and then we’ll solve it concurrently.

 */
public class Sample {

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
