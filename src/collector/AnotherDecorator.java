package collector;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

/*
Reduce Intro : Stream reduction is an operation that returns one value by combining the elements of a stream.
The Java stream API contains a set of predefined reduction operations, such as average(), sum(), min(), max(),
and count(). There is one general-purpose operation: reduce().

reduce() is a method for generating custom reduction operations on a stream.

reduce(T identity, BinaryOperator<T> accumulator)
“identity” is the initial value of the reduction operation and the default result if the stream is empty.
In other words, “what to start with”.

“accumulator” is a function for combining two values.

    BinaryOperator is a special BiFunction. So you can assign the same expression to both of them. Check this out.

    BinaryOperator<Integer> foo = (a, b) -> {
        return a * a + b * b;
    };
    BiFunction<Integer, Integer, Integer> barFn = (a, b) -> {
        return a * a + b * b;
    };
    If you look at the source code, it would be

    public interface BinaryOperator<T> extends BiFunction<T,T,T> {
       // Remainder omitted.
    }

 */
@SuppressWarnings("Unchecked")
class Camera {

    private Function<Color, Color> filter;

    //Constructor will accept a lot of filters
    public Camera(Function<Color, Color>... filters) {
        // Given an input return the input back to me. This is identity filter
        filter = input -> input;
        for(Function<Color, Color> aFilter: filters) {
            // This combines all the filters given to us to form a bigger filter or pipeline of filters
            filter = filter.andThen(aFilter);
        }

        filter = Stream.of(filters)
                .reduce(input -> input, (aFilter, result) -> result.andThen(aFilter));

        filter = Stream.of(filters)
                .reduce(Function.identity(), Function::andThen);

    }
    public Color snap (Color input) {
        return filter.apply(input);
    }

}

public class AnotherDecorator {

    public static void printIt(Camera camera) {
        System.out.println(camera.snap(new Color(125, 125, 125)));
    }
    public static void main(String[] args) {
        printIt(new Camera());
        printIt(new Camera(Color::brighter));
        printIt(new Camera(Color::darker));

        //Now you can start using decorator

        printIt(new Camera(Color::brighter, Color::darker));
    }
}
