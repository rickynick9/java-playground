package collector;

import java.util.function.Function;

public class Decorator {
    // Composibility is one of the most essential aspect of functional programming.
    // Composing functions together


    private static void printIt(int n, String msg, Function<Integer, Integer> func) {
        System.out.println(n + " " + msg + " : "+func.apply(n));
    }

    public static void main(String[] args) {
        Function<Integer, Integer> inc = n -> n + 1;
        Function<Integer, Integer> doubled = n -> n * 2;

        printIt(5, "incremented", inc);
        printIt(10, "incremented", inc);

        printIt(5, "doubled", doubled);
        printIt(10, "doubled", doubled);

        // Incremented and doubled
        // We can define another Function as below but that would not be right approach.
        Function<Integer, Integer> incAndDoubled = e -> (e + 1) * 2;

        // We can rathwer use function composition
        printIt(5, "incremented and doubled", inc.andThen(doubled));

        // We can now start decorating our codes with the help of function composition

    }
}
