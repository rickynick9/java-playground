import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/*
Function Descriptor is a term used to describe the signature of the abstract method of a Functional Interface
. The signature of the abstract method of a Functional Interface is syntactically the same as the signature of the Lambda Expression
. Hence, a Function Descriptor also describes the signature of a lambda.
 */
public class Main {
    public static void main(String[] args) {
        List<String> cities = Arrays.asList("Sydney", "Dhaka", "New York", "London");

        Consumer<List<String>> upperCaseConsumer = list -> {
            for(int i=0; i< list.size(); i++){
                list.set(i, list.get(i).toUpperCase());
            }
        };
        Consumer<List<String>> printConsumer = list -> list.stream().forEach(System.out::println);

        upperCaseConsumer.andThen(printConsumer).accept(cities);

        Consumer<String> c1 = String::toUpperCase;
        Consumer<String> c2 = c-> System.out.println(c);

        c1.andThen(c2).accept("hello");

        //ArrayDeque
        List<String> friends = Arrays.asList("Nishant", "Prateek", "Kunal", "Nilesh", "Kapoor", "Namit");
        final Predicate<String> startsWithN = (t) -> t.startsWith("N");
        final Predicate<String> startsWithK = (t) -> t.startsWith("K");
        System.out.println("Starts with N count : "+friends.stream().filter(startsWithN).count());
        System.out.println("Starts with K count : "+friends.stream().filter(startsWithK).count());

        //Removing Duplication with Lexical Scoping. we could extract the letter as a parameter to a function and pass
        // the function as an argument to the filter() method.
        System.out.println("Starts with N count*** : "+friends.stream().filter(startsWithLetter("N")).count());
        System.out.println("Starts with K count*** :"+friends.stream().filter(startsWithLetter("K")).count());

        Predicate<Integer> grt_10 = x -> (x > 10);
        Predicate<Integer> less_100 = x -> (x < 100);

        // composing two predicates using and
        System.out.println(grt_10.and(less_100).test(60));

        Map<String, Integer> iqMap = makeMap();
        iqMap.entrySet().removeIf(e -> e.getValue() <=100);
        System.out.println(iqMap);



    }

    public static Predicate<String> startsWithLetter(String letter) {
        return (t) -> t.startsWith(letter);
    }

    public static Map<String, Integer> makeMap() {
        return new ConcurrentHashMap<>() {{
            put("Larry", 100); put("Curly", 90); put("Moe", 110);
        }};
    }

    public static <T> List<T> filter(T[] array, Predicate<T> predicate) {
        return Arrays.stream(array).filter(predicate).collect(Collectors.toList());
    }

}
