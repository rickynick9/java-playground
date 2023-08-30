package concurrency.IO.AQI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherApi {

    public static String getAQI(String city) throws IOException {
        final URL url = new URL("https://api.ambeedata.com/latest/by-city?city="+city);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("x-api-key", "ab1c6eb60969970b0354f3d569873f21feb49e276adc61acafc4fd75fbd2f88f");


        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine())!= null) {
            output.append(line);
        }
        //System.out.println(output);
        return output.toString();
    }

}
