package designpatterns;

import java.util.ArrayList;
import java.util.List;

public class IteratorPattern {
    public static void main(String[] args) {
        // Earlier we had external iterator - We control the iteration in every step
        var names = List.of("Nishant", "Nikhil", "Ajay", "Prashant", "Kishore", "Amit");

       // for(int i =0; i< names.size(); i++) //  Verbose

        for(var name: names) {
            if(name.length() == 5) {
                System.out.println(name.toUpperCase());
            }
        }

        // Functional way - internal iterator because we are not in the business of controlling the iteration
        names.stream()
                .filter(n -> n.length() == 5)
                .map(String::toUpperCase)
                .limit(2)
                .forEach(System.out::println);

        // limit is break equivalent in functional programming

        //Another example
     var result1 = new ArrayList<String>();
     for(var name: names) {
         if(name.length() == 4) {
             result1.add(name.toUpperCase());
         }
     }

     // Equivalent example in functional style
        var result2 = new ArrayList<String>();
        names.stream()
                .filter(name -> name.length() == 4)
                .map(String::toUpperCase)
                .forEach(n -> result2.add(n));
                // .toList() is a better option


        // Bad idea because we are mutating global variable in functional programming
        // The problem here is, functional pipeline is not pure
        // We are doing shared mutability
        // Analogy : Change your clothes but never do that in public

        // The result of above code may be unpredictable if we ever change this code to run in parallel
        // by adding .parallel or by changing .stream to .parallelStream()

        // Functional pipeline offers internal iterators
        // is less complex
        // easy to modify and understand
        // but ....

        // It is very important that we make the functional pipeline pure
        // Avoid shared mutable variables

        // What is a pure function ?
        // A pure function is idempotent, it returns the same result for the same input and does not have any side-effects
        // rules of purity
        // 1. It does not change any state that is visible outside
        // 2. It does not depend on anything outside that may possibly change
        // If you are in the middle of your pipeline and the outside variable changes then your pipeline behaviour is
        // no longer the same












    }
}
