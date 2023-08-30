package concurrency.IO.AQI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ImperativeConcurrentAQI extends AbstractAQI {

    @Override
    double computeAvgAQI() throws IOException, InterruptedException, ExecutionException {
        final int numberOfCores = Runtime.getRuntime().availableProcessors();
        final double blockingCoefficient = 0.9;
        final int poolSize = (int) (numberOfCores / (1 - blockingCoefficient));
        System.out.println("Number of Cores available is " + numberOfCores);
        System.out.println("Pool size is " + poolSize);

        List<String> cities = getCities();
        List<Callable<Double>> aqiTaskList = new ArrayList<>();
        for(String city: cities) {
            Callable<Double> aqiTask = () -> transform(city);
            aqiTaskList.add(aqiTask);
       }

        final ExecutorService executorPool = Executors.newFixedThreadPool(poolSize);
        final List<Future<Double>> aqiValues = executorPool.invokeAll(aqiTaskList, 10000, TimeUnit.SECONDS);

        double netAqiValue = 0.0;
        for(final Future<Double> aqi : aqiValues) {
            netAqiValue += aqi.get();
        }
        return netAqiValue / aqiValues.size();
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        new ImperativeConcurrentAQI().timeAndCompute();
    }
}

/*
Output
Number of Cores available is 8
Pool size is 80
###### Average AQI : 72.33333333333333
Time (seconds) taken 0.965968101
 */
