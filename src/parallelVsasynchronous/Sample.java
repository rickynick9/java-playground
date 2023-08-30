package parallelVsasynchronous;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


public class Sample {

    // Stream : Its internal iterator. You do not focus on iteration.
    // In imperative style, the structure of sequential code is very different from structure of concurrent code.
    // The entire code structure changes if we try to make sequential code parallel in imperative code. The innocent
    // looking code turns into a monster

    // Mutability and parallelism do not go hand in hand. In imperative style when you have mutability the real challenge
    // is correctness of code when is comes to parallelism.

    // Using streams, the structure of sequential code is identical to structure of concurrent code.
    // Rather than calling parallelStream, you can also call parallel method on stream to run the code in parallel

    /*
    Streams                                 Reactive streams
    sequential vs parallel                  synchronous vs asynchronous

    Entire pipeline is either               Depends
    parallel or sequential.                 subscribeOn - No segments
    No segments, you cannot run             observeOn - segments
    few functions sequentially and few
    in parallel.
     */

    /*
    Java 1 -> We created Threads. We realized that threads are not light weight and it's not a good idea to create
    threads after threads. This problem was solved in Java 5

    Java 5 ->  Java 5 introduced ExecutorService. We had a pool of threads and evetually we realized its a pool of
    problems.

    Pool induced deadlock
     */
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbers.parallelStream()
                .map(e -> transform(e))
                .forEach(e -> {});
                //.forEach(System.out::println);

        //use(numbers.stream());
    }

    public static void use(Stream<Integer> stream) {
        stream.parallel()
                .map(e -> transform(e))
                .forEach(System.out::println);
    }

    private static int transform(Integer number) {
        System.out.println("t: "+number+ "---"+Thread.currentThread());
        sleep(1000);
        return number * 1;
    }

    private static boolean sleep(int ms) {
        try {
            Thread.sleep(ms);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }


}
