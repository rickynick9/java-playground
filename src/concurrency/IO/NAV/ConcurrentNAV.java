package concurrency.IO.NAV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ConcurrentNAV extends AbstractNAV {
    /*
    1. determine the thread pool size based on the presumed blocking coefficient and the number of cores
    2. place each part—to fetch the price for each ticker symbol—into the anonymous code block of the Callable interface.
    3. schedule these parts on the fixed-size pool using the invokeAll() method.

    The executor takes the responsibility of concurrently running as many of the parts as possible.
    If there are more divisions than the pool size, they get queued for their execution turn.

    Mutation :
    The only mutable variable we have in the previous code is netAssetValue.
    This mutation happens only in one thread, the main thread—so we have only isolated mutability here and
    not shared mutability.

    Since there is no shared state, there is nothing to synchronize in this example. With the help of Future,
    we were able to safely send the result from the threads fetching the data to the main thread.

    Limitation:
    There’s one limitation to the approach in this example. We’re iterating through the Future objects in the loop
     So, we request results from one part at a time, pretty much in the order we created/scheduled the divisions.
     Even if one of the later parts finishes first, we won’t process its results until we process the results of parts
     before that. In this particular example, that may not be an issue. However, if we have quite a bit of computation
     to perform upon receiving the response, then we’d rather process results as they become available instead of waiting
     for all tasks to finish.

     We could use the JDK CompletionService for this. We’ll revisit this concern and look at some alternate solutions later.

    Speedup for the IO-Intensive App:
    The nature of IO-intensive applications allows for a greater degree of concurrency even when there are fewer cores.
    When blocked on an IO operation, we can switch to perform other tasks or request for other IO operations to be started.
    We estimated that on a two-core machine, about twenty threads would be reasonable for the stock total asset value
    application.

    Let’s analyze the performance on a two-core processor for various numbers of threads—from one to forty.
    Since the total number of divisions is forty, it would not make any sense to create more threads than that.
    We can observe the speedup as the number of threads is increased.
    The curve (Plot b/w Time in seconds vs Pool size i.e number of threads) begins to flatten right about twenty threads
    in the pool. This tells us that our estimate was decent and that having more threads beyond our estimate will not help.
     */
    public double computeNetAssetValue(final Map<String, Integer> stocks) throws InterruptedException, ExecutionException {
        final int numberOfCores = Runtime.getRuntime().availableProcessors();
        final double blockingCoefficient = 0.9;
        final int poolSize = (int) (numberOfCores / (1 - blockingCoefficient));
        System.out.println("Number of Cores available is " + numberOfCores);
        System.out.println("Pool size is " + poolSize);

        final List<Callable<Double>> partitions = new ArrayList<>();
        for (final String ticker : stocks.keySet()) {
            partitions.add(() -> stocks.get(ticker) * YahooFinance.getPrice(ticker));
        }

        final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
        final List<Future<Double>> valueOfStocks = executorPool
                .invokeAll(partitions, 10000, TimeUnit.SECONDS);

        double netAssetValue = 0.0;
        for (final Future<Double> valueOfAStock : valueOfStocks)
            netAssetValue += valueOfAStock.get();

        executorPool.shutdown();
        return netAssetValue;
    }

    public static void main(final String[] args)
            throws ExecutionException, InterruptedException, IOException {
        new ConcurrentNAV().timeAndComputeValue();
    }
}
