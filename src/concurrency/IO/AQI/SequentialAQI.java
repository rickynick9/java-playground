package concurrency.IO.AQI;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SequentialAQI extends AbstractAQI {

    public double computeAvgAQI() throws IOException {

        Map<String, Double> aQIMap = getCities()
                .stream()
                .collect(Collectors.toMap(Function.identity(), AbstractAQI::transform));

        Double aQIAvg = aQIMap.values().stream().mapToDouble(d -> d).sum() / (long) aQIMap.values().size();
        aQIMap.forEach((k, v) -> {
            System.out.println(k + " ---> "+v);
        });

        return aQIAvg;
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        new SequentialAQI().timeAndCompute();
    }

    /*
    Output:
    ###### Average AQI : 72.33333333333333
    Time (seconds) taken 9.470688323
     */
}
