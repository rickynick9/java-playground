package predicate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericValidator {

    public static void main(String[] args) {
        Predicate<Integer> lessThan20 = num -> num < 20;
        System.out.println(lessThan20.test(25));

        // Basic Filter
        List<String> names = Arrays.asList("Adam", "Alexander", "John", "Tom");
        List<String> result0 = names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());

        // Multiple filter
        List<String> result1 = names.stream()
                .filter(name -> name.startsWith("A"))
                .filter(name -> name.length() < 5)
                .collect(Collectors.toList());

        // Complex Predicate
        List<String> result2 = names.stream()
                .filter(name -> name.startsWith("A") && name.length() < 5)
                .collect(Collectors.toList());

        //Combining predicates
        Predicate<String> predicate1 =  str -> str.startsWith("A");
        Predicate<String> predicate2 =  str -> str.length() < 5;
        List<String> result3 = names.stream()
                .filter(predicate1.and(predicate2))
                .collect(Collectors.toList());

        // Combine Predicates Inline
        List<String> result4 = names.stream()
                .filter(((Predicate<String>)name -> name.startsWith("A"))
                        .and(name -> name.length()<5))
                .collect(Collectors.toList());

        // Combining a Collection of Predicates
        List<Predicate<String>> allPredicates = new ArrayList<Predicate<String>>();
        allPredicates.add(str -> str.startsWith("A"));
        allPredicates.add(str -> str.contains("d"));
        allPredicates.add(str -> str.length() > 4);

        List<String> result5 = names.stream()
                .filter(allPredicates.stream().reduce(x->true, Predicate::and))
                .collect(Collectors.toList());



    }




}
