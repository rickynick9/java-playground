package collector;

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

    }

    // This code is an example of very low signal to noise ratio. There is lot of verbose code.
    // What we are really trying to do is to pass a piece of code of function to this particular thread
    // But we could not do this in past because Functions were not first class citizen in Java. We are treating function as Kindergarten children

    // We started wrapping functions with objects for no good reason. Analogy : If a kid wants to go to a park to play
    // we tell them you cannot go alone, let me send an adult with you

    // Lambdas are anonymous functions and we can pass these anonymous functions around

    //Strategy Pattern
    /*
    In traditional strategy pattern, you have a class that wants to vary part of its implementation.
    To vary part of implementation in a function, what would yoy typically do, you would pass an interface to it.
    So, you have an interface and several implementation.

     If you really want to implement Strategy pattern, you don't have to invest your time in interfaces and classes
     You can simply use lambda expressions and functional interfaces to get things done.
     */




}
