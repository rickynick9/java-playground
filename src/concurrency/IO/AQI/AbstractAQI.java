package concurrency.IO.AQI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractAQI {

        List<String> getCities() throws IOException {
            List<String> cities = new ArrayList<>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/nishant/Bimaconnect/Java-Nish/src/concurrency/IO/AQI/cities.txt"));
            String city = null;
            while((city = bufferedReader.readLine()) != null) {
                cities.add(city.trim());
            }
            return cities;
        }

        public void timeAndCompute() throws IOException, ExecutionException, InterruptedException {
            final Long start = System.nanoTime();
            Double aQIAvg = computeAvgAQI();
//            Map<String, Double> aQIMap = getCities()
//                    .stream()
//                    .collect(Collectors.toMap(Function.identity(), AbstractAQI::transform));
//
//            Double aQIAvg = aQIMap.values().stream().mapToDouble(d -> d).sum() / (long) aQIMap.values().size();
//            aQIMap.forEach((k, v) -> {
//                System.out.println(k + " ---> "+v);
//            });
            System.out.println("###### Average AQI : "+aQIAvg);
            final Long end = System.nanoTime();
            System.out.println("Time (seconds) taken " + (end - start)/1.0e9);

            // 1.0E9 number actually is. 1.0 x 10^9
        }

        public static double transform(String city) {
            try {
                String response = WeatherApi.getAQI(city);
                JSONObject jsonObject = new JSONObject(response);
                JSONArray arr = jsonObject.getJSONArray("stations");
                return Double.parseDouble(arr.getJSONObject(0).get("AQI").toString());
            } catch (IOException e) {
                System.out.println("Exception during api call :"+e);
            }
            return 0L;
        }

        abstract double computeAvgAQI() throws IOException, InterruptedException, ExecutionException;
}

