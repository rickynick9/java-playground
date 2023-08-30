package designpatterns;

import java.util.function.Supplier;

public class Lazy<T> {

    private T instance;
    private Supplier<T> supplier;

    public Lazy(T value) {
        instance = value;
    }
    // When yoy look at this code, it is pretty eager. Because the minute you want to pass a value that got pre-evaluated.
    // What you are saying is, we do not want to evaluate it yet, What I want to do instead is to take a supplier
    // I do not want to store value but want to store the Supplier of value i.e T in this case;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        return instance;
    }

    public T getInstance() {
        if(instance == null) {
            instance = supplier.get();
        }
        return instance;
    }

    // I have deferred the instance creation until it is requested


    public static void main(String[] args) {
        int value = 4;
        //int temp = compute(value); // This is eager evaluation

        Lazy<Integer> tempL = new Lazy<>(() -> compute(value));
        // You are passing supplier as value. You are also saying that I do not want the value right away.

        if(value > 4 && tempL.get() > 100) {
            System.out.println("PATH 1 with :"+tempL.get());
        } else {
            System.out.println("PATH 2");
        }

    }

    public static int compute (int number) {
        // Imagine it takes some time to compute
        System.out.println("Compute called ....");
        return number * 100;
    }

    // myFunction(Type value) --> Eager evaluation
    // myFunction(Supplier<Type> supplier) --> Lazy Evaluation
    // When you are designing the function, you need to ask if you want it to be evaluated right
    // away or you want to postpone the evaluation

    /*
        Laziness is ultimate performance optimization technique.
        Before trying to inline, parallelize, optimize a piece of code, ask yourself if you could avoid to run it at all.
        Laziness is probably one form of performance optimization which is (almost) never premature.

        A stream is not a data structure, it is a lazy specification on how to manipulate a set of data.

     */

    // . Similarly, current support for eager evaluation
    //in lazy languages such as Haskell [3] is practically non-existent.

    /*
    class InsurancePremiumCalculator {
        boolean ask(String question) {
            boolean ans;
            // display a window with question and yes/no
            // buttons, block until one is pressed
                ...
            return ans;
        }
        int calculatePremium() {
            if (ask("Do you smoke?"))
                return 1000;
            boolean drinker=ask("Do you drink?");
            if (ask("Do you sky-dive?") && !drinker)
                ...
            else if (drinker && ...)
                ...
            ...
        }
    }


    class InsurancePremiumCalculator {
        boolean ask(String question) {
            boolean ans;
            // display a window with question and yes/no
            // buttons, block until one is pressed
            ...
            return ans;
        }
        int calculatePremium() {
            return premiumCalculatorLogic(
                ask("Do you sky-dive?"),
                ask("Do you smoke?"),
                ...,
                ask("Do you drink?")
            );
        }
        int premiumCalculatorLogic(lazy boolean skydiver,
        lazy boolean smoker,
        ...,
        lazy boolean drinker) {
            if (smoker)
                return 1000;
            else if (skydiver && !drinker)
            ...
            else if (drinker && ...)
            ...
            ...
        }
}

     */
}
