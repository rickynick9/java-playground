package collector;

import java.util.function.Supplier;

class Lazy<T> {
    private T instance;
    private Supplier<T> supplier;

    public Lazy(Supplier<T> theSupplier) {
        supplier = theSupplier;
    }

    public T get() {
        if(instance == null) {
            instance = supplier.get();
            supplier = null;
        }
        return instance;
    }
}

public class DesignDelegate {

    public static int compute(int n) {
        System.out.println("Called ...");
        return n * 2;
    }

    public static void main(String[] args) {

        //defers evaluation until needed
        int x = 4;

        //int temp = compute(x);

        if(x > 5 && compute(x) > 7)
            System.out.println("Path 1");
        else
            System.out.println("Path 2");

        /*
        Output in this case
        Called ...
        Path 2

        However, when we have this if condition

        if(x > 5 && compute(x) > 7)
            System.out.println("Path 1");
        else
            System.out.println("Path 2");

        Output in this case is
        Path 2

         */

        // We can introduce lazy evaluation in this case. We will not call compute() directly but we will introduce
        // lambda which will in-turn call compute

        var temp = new Lazy<Integer>(() -> compute(x));

        // In this case we are creating an object called Lazy. We are telling Lazy object to receive constructor call
        // which takes the lambda.

        if(x > 5 && temp.get() > 7)
            System.out.println("Path 11");
        else
            System.out.println("Path 22");

    }
}
