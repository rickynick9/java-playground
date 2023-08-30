package concurrency.IO.AQI;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConcurrentAQI extends AbstractAQI {
    public double computeAvgAQI() throws IOException {

        Map<String, Double> aQIMap = getCities()
                .parallelStream()
                .collect(Collectors.toMap(Function.identity(), AbstractAQI::transform));

        Double aQIAvg = aQIMap.values().stream().mapToDouble(d -> d).sum() / (long) aQIMap.values().size();
        aQIMap.forEach((k, v) -> {
            System.out.println(k + " ---> "+v);
        });

        return aQIAvg;
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        new ConcurrentAQI().timeAndCompute();
    }

}
