import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainGenerics {

    public static void main(String[] args) {

        /*
        Type arguments without bounds are useful, but have limitations.
        If you declare a List of unbounded type, as in A List with an unbounded wildcard, you can read from it but not write to it.
        */

        List<?> stuff = new ArrayList<>();
//        stuff.add("abc");
//        stuff.add(new Object());
//        stuff.add(3);
        int numElements = stuff.size();

        /*
        That feels pretty useless, since there’s apparently no way to get anything into it.
        One use for them is that any method that takes a List<?> as an argument will accept any list at all
        when invoked (Unbounded List as a method arg).
         */

        List<String> stringList = new ArrayList<>(){{add("Nishant"); add("Nikunja");}};
        List<Integer> integerList = new ArrayList<>(){{add(10); add(20);}};
        printList(stringList);
        printList(integerList);

        /*
        Upper Bounded Wildcards
        An upper bounded wildcard uses the extends keyword to set a superclass limit.
        For example, to define a list of numbers that will allow ints, longs, doubles, and even BigDecimal
        instances to be added to it,
         */
        List<? extends Number> numbers = new ArrayList<>();
//        numbers.add(3);
//        numbers.add(3.14159);
//        numbers.add(new BigDecimal("3"));

        /*
        Well, that seemed like a good idea at the time. Unfortunately, while you can define the list with the
        upper bounded wildcard, again you can’t add to it. The problem is that when you retrieve the value,
        the compiler has no idea what type it is, only that it extends Number.
        */

        /*
        Still, you can define a method argument that takes List<? extends Number> and then invoke the method with
        the different types of lists
         */

        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> doubles = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0);
        List<BigDecimal> bigDecimals = Arrays.asList(
                new BigDecimal("1.0"),
                new BigDecimal("2.0"),
                new BigDecimal("3.0"),
                new BigDecimal("4.0"),
                new BigDecimal("5.0")
        );

        System.out.printf("ints sum is        %s%n", sumList(ints));
        System.out.printf("doubles sum is     %s%n", sumList(doubles));
        System.out.printf("bigdecimals sum is %s%n", sumList(bigDecimals));

        /*
        Lower Bounded Wildcards
        You use the super keyword with the wildcard to specify a lower bound. In the case of a List<? super Number>,
        the implication is that the reference could represent List<Number> or List<Object>.
         */

        // With the upper bounded list, we were extracting values and using them.
        // With the lower bounded list, we supplied them. This combination has a traditional name: PECS.

        /*
        The term PECS stands for "Producer Extends, Consumer Super", which is an odd acronym coined by Joshua Block
        in his Effective Java book but provides a mnemonic on what to do. It means that if a parameterized type represents
        a producer, use extends. If it represents a consumer, use super. If the parameter is both, don’t use wildcards at all

        The advice boils down to:
            Use extends when you only get values out of a data structure
            Use super when you only put values into a data structure
            Use an explicit type when you plan to do both

         */

    }

    //Unbounded List as a method arg
    private static void printList(List<?> list) {
        System.out.println(list);
    }

    private static double sumList(List<? extends Number> list) {
        // Extracting a value from an upper bound reference
        // When you access an element from the list with an upper bound, the result can definitely be
        // assigned to a reference of upper bound type
        Number num = list.get(0);

        return list.stream()
                .mapToDouble(Number::doubleValue) // returns DoubleStream
                .sum();
    }

    // by making the second argument of type List<? super Integer>, the supplied list can be of type
    // List<Integer>, List<Number>, or even List<Object>,
    public void numsUpTo(Integer num, List<? super Integer> output) {
        IntStream.rangeClosed(1, num)
                .forEach(output::add);
    }


}
