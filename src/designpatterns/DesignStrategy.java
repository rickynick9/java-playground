package designpatterns;

import java.util.List;
import java.util.function.Predicate;

public class DesignStrategy {

    public static int totalValueFunctional(List<Integer> values, Predicate<Integer> selector) {
        return values.stream()
                .filter(selector)
                .mapToInt(e -> e)
                .sum();
    }

    public static int totalValueI(List<Integer> values, Predicate<Integer> selector) {
        int total = 0;

        for (int e: values) {
            //If selector.test() on element is true then total it otherwise ignore it
            if(selector.test(e)) total += e;
        }

        return total;
    }

    public static int totalValue(List<Integer> values) {
        int total = 0;

        for (int e: values) {
            total += e;
        }

        return total;
    }

    public static int totalEvenValue(List<Integer> values) {
        int total = 0;

        for (int e: values) {
            if(e % 2 ==0) total += e;
        }

        return total;
    }

    public static int totalOddValue(List<Integer> values) {
        int total = 0;

        for (int e: values) {
            if(e % 2 !=0) total += e;
        }

        return total;
    }

    private static boolean isEven(Integer n) {
        return n % 2 == 0;
    }

    private static boolean isOdd(Integer n) {
        return n % 2 != 0;
    }

    public static void main(String[] args) {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("In another thread");
            }
        });

        th.start();
        System.out.println("In Main");

        Thread th1 = new Thread(() -> System.out.println("In another thread 1"));
        th1.start();

        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(totalValue(numbers));

        //Not only the total value, we also want total even value
        System.out.println(totalEvenValue(numbers));
        //Now your boss says, we want total odd value as well
        // Copying and pasting the code for the third time is criminal negligence

        System.out.println(totalOddValue(numbers));


        // Using new function where we have introduced Predicate
        System.out.println("Total Value : "+totalValueI(numbers, e -> true));
        System.out.println("Total Even Value :"+totalValueI(numbers, e -> e % 2 == 0));
        System.out.println("Total Odd Value :"+totalValueI(numbers, e -> e % 2 != 0));

        // We are using strategy pattern here and the Strategy is determining what values to total.
        // You can use Predicate to implement strategy
        // We can also define even/odd function and make the code further concise

        System.out.println("Total Even Value -- "+totalValueFunctional(numbers, DesignStrategy::isEven));
        System.out.println("Total Odd Value -- "+totalValueFunctional(numbers, DesignStrategy::isOdd));

        // Usually I create a business strategy Utility class so that all strategies can be put together
        // System.out.println("Total Odd Value -- "+totalValueFunctional(numbers, BusinessStrategy::isOdd));

        // Remember strategies are stateless and that brings us back to purity of functions and lambda expressions.
        // Patterns tend to evolve as time goes by. Strategy becomes a feture rather than standing out so separately.
        // You are not putting efforts to create interfaces and classes anymore so this becomes a lot easier to build.



    }

    // This code is an example of very low signal to noise ratio. There is lot of verbose code.
    // What we are really trying to do is to pass a piece of code of function to this particular thread
    // But we could not do this in past because Functions were not first class citizen in Java. We are treating function as Kindergarten children

    // We started wrapping functions with objects for no good reason. Analogy : If a kid wants to go to a park to play
    // we tell them you cannot go alone, let me send an adult with you

    // Lambdas are anonymous functions and we can pass these anonymous functions around

    //Strategy Pattern
    /*
    Strategies are often single method or function -> So functional interfaces and lambdas work really well for them.
    Lambdas fit in really well for strategies in general.

    Sometimes we go in wrong direction not because of the intent but because of the environment.
    Java had the ideology of being object-oriented. Unfortunately though when Java introduced anonymous inner classes
    that was a missed opportunity because iof they had introduced lambdas back then we would have gone in different
    direction 20 years ago. In case of Java, the ideology of object oriented concept was pushed further and we
    refused to think functions as a first class citizen. The route we took was to really build classes that were
    wrapper around functions. We were treating functions as kindergarteners. This little child comes to you and say
    "Can I go to the park please ?" You say, no sweetie you cannot, let me send an adult with you. So we wrapped this
    function into object and pushed the object around for no good reason.

    In traditional strategy pattern, you have a class that wants to vary part of its implementation.
    To vary part of implementation in a function, what would yoy typically do, you would pass an interface to it.
    So, you have an interface and several implementation.

     If you really want to implement Strategy pattern, you don't have to invest your time in interfaces and classes
     You can simply use lambda expressions and functional interfaces to get things done.
     */




}
