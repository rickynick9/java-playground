package concurrency.IO.NAV;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


/*
many times we need to read the input from an external source, such as a file or keyboard

BufferedReader reads text from a character-input stream to provide efficient reading of characters, arrays, and lines.

BufferedReader is best used with an underlying reader whose read operation in itself is inefficient, but when wrapped
around by a BufferedReader, it becomes highly efficient.

The underlying stream reader can be any FileReaders or the InputStreamReader.
 */
public class YahooFinance {
    public static double getPrice(final String ticker) throws IOException {
        // https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=IBM&apikey=AN4Q9B5HKZJKVO27
        // IBM, tesla, tencent, google
        final URL url =
                new URL("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="+ticker+"&apikey=AN4Q9B5HKZJKVO27&datatype=csv");
        final BufferedReader reader = new BufferedReader( new InputStreamReader(url.openStream()));

        //symbol	open	high	low	price	volume	latestDay	previousClose	change	changePercent
        //IBM	143.44	143.95	142.85	143.45	6686627	28/07/23	142.97	0.48	0.34%

        final String discardHeader = reader.readLine();
        final String data = reader.readLine();
        //System.out.println(data);

        final String[] dataItems = data.split(",");
        //System.out.println(dataItems);
        //System.out.println(dataItems[dataItems.length - 6]);
        System.out.println("Stock name :"+ticker);
        final double priceIsTheLastValue = Double.parseDouble(dataItems[dataItems.length - 6]);
        return priceIsTheLastValue;
    }

}
