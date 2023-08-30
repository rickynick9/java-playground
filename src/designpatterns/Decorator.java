package designpatterns;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

public class Decorator {

    /*
    Functions are composable
     */
    public static void print(int number, String msg, Function<Integer, Integer> func) {
        System.out.println(number + " "+func.apply(number) + " "+msg);
    }
    public static void main(String[] args) {
        Function<Integer, Integer> inc = value -> value + 1;
        Function<Integer, Integer> doubled = value -> value * 2;

        print(5,"incremented", inc);
        print(5,"doubled", doubled);
        print(5,"incrementedAnddoubled", inc.andThen(doubled));
        //take o/p of increment and pass it on to doubled function. Essentially we are making a functional pipeline

        print(new Camera()); //same output as input. nothing really exciting here
        print(new Camera(Color::brighter));
        print(new Camera(Color::darker));

        //composition of functions
        print(new Camera(Color::brighter, Color::darker));

        // You want to validate data, you want to encrypt data etc
        // different types of validations can be added as decorator. You can provide any combination.


    }

    public static void print(Camera camera) {
        System.out.println(camera.snap(new Color(255, 255, 255)));
    }

}

    /*
    Think of camera as a composition of lenses. Variety of lens combination produces different results
     */

class Camera {

    private Function<Color, Color> filter;
    public Camera(Function<Color, Color>... filters) {
        filter = input -> input;
        //combining functions into a pipeline
        filter = Stream.of(filters)
                .reduce(Function.identity(), Function::andThen);
        //This is taking all discrete functions and taking it into a pipeline and form one reduced function
        // of the pipeline

    }

    public Color snap(Color input) {
        //the snap I'm going to take, take color as input and apply the filter on color
        return filter.apply(input);
    }

}