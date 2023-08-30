package immutability;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class EffectivelyFinal {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3);

        Stream<Integer> stream = numbers.stream()
                .map(e -> e * 2); // pure because it does not affect anything and not dependent on anything that
                                  // may possibly change

        //numbers.stream().forEach(e -> numbers.remove(e)); -> Cannot modify stream while executing

        /*
        For most data sources, preventing interference means ensuring that the data source is not modified at all during
        the execution of the stream pipeline. The notable exception to this are streams whose sources are concurrent
        collections, which are specifically designed to handle concurrent modification. Concurrent stream sources are
        those whose Spliterator reports the CONCURRENT characteristic.
         */

        stream.forEach(System.out::println);

        int factor = 2;
        Stream<Integer> anotherStream = numbers.stream()
                .map(e -> e * factor);

        //factor = 5; -> You cannot do this because all variables used in lambda expression should be effectively final.
        // Even though we have not specified the factor as final it is considered by compiler as final because its used
        // in lambda expression. Java says that I'll treat this factor variable as final as long as you do not mess around

        int factorA[] = new int[] {2}; // Don't even do this. this is pure evil
        Stream<Integer> anotherStreamI = numbers.stream()
                .map(e -> e * factorA[0]);

        factorA[0] = 0;
        anotherStreamI.forEach(System.out::println);

    }
}
