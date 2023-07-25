package collector;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectorTest {
    /*
    WATCHED 10  MINS : https://www.youtube.com/watch?v=pGroX3gmeP8&t=2075s


    Collector is a collection of utility functions
    When it comes to functional style of programming, we typically have few functions that we use : filter, map, reduce
    Just like imperative programming where we use if/else or for loop, we tend to use these functions quite a bit.
     */

    public static List<Person> createPeople() {
        return List.of(
                new Person("Sara", 20),
                new Person("Sara", 22),
                new Person("Bob", 20),
                new Person("Paula", 32),
                new Person("Paul", 32),
                new Person("Jack", 3),
                new Person("Jack", 72),
                new Person("Jill", 11)
        );

    }

    public static void main(String[] args) {
        //If we want to print all people
        // foreach uses internal iterator
        createPeople()
                .forEach(System.out::println);

        // what if we do not want to print everyone's details but only people who are older than 30 years
        // streams are internal iterators, streams give us wonderful lazy evaluation
        createPeople()
                .stream()
                .filter(p -> p.getAge() > 30)
                .forEach(System.out::println);

        /*
        Before Java 8, we could use filter directly on the List itself. stream was not needed
             createPeople().
                .filter()
                .forEach(System.out::println);
        But ofcourse, they removed it along the way and the reason for doing this is because function foreach
        is an eager evaluation, We also know that its terminal operation as well. On the other hand filter gives us
        lazy evaluation. When we add stream(), it means we are no longer in a eager evaluation, we are rather into
        lazy evaluation. Because of the laziness in this case, stream is not going to evaluate filter right away until
        ofcourse we hit the terminal operation.

        If we remove forEach from above then filter opreation will not be evaluated. It will be evaluated only when
        terminal operation is encountered.
         */

        // Map Function
        /*
        get me the name of the person. I do not really care about age value
        We are performing transformation of Person by using map function
        map is also intermediate evaluation. you would not get result until you hit terminal operation
         */
        createPeople()
                .stream()
                .filter(p -> p.getAge() > 30)
                .map(p -> p.getName())
                .forEach(System.out::println);

        // Reduce function
        /*
        If I want to total the age of people in this collection,
        we can start with an identifier 0 to begin with

        1. reduce takes a collection and reduces it to single value
        2. reduce converts a stream to something concrete
        This means, Jave has reduce in 2 forms.
        reduce function and
        collect function - The net effect of collect is to reduce. This means reduce can reduce collection of Person to
        collection of age, collection of names etc.
         */

        Integer totalAgeA =  createPeople()
                .stream()
                .map(p -> p.getAge())
                .reduce(0, (total, age) -> total + age);


       Integer totalAge =  createPeople()
                .stream()
                .map(p -> p.getAge())
                //.reduce(0, (total, age) -> total + age);
               //.reduce(0, (total, age) -> Integer.sum(total, age));
               .reduce(0, Integer::sum);

       // Functional programming
        /*
        When we talk about functional programming, we discuss immutability and higher order function but we miss
        the subtle aspect of it.

        Object Oriented Programming : The whole essence is polymorphism
        Without polymorphism, any language is object based language but not object oriented language

        Similarly the most important aspect of functional programming is
        Functional Composition + Lazy Evaluation
        Laziness gives us efficiency, without laziness we end going from collection to another collection to another
        Eager evaluation will create lot more collection for intermediate operations. So, the more garbage we create,
        the more garbage we have to collect. So one of the nice things about lazy evaluation is it postpones create
        objects and we are efficient in evaluation.

        We we take away laziness, we have beautiful syntax of functional composition but we do not have symantics.
        So the symantics of functional programming is really about laziness.

        What is lazy evaluation dependant on ?
        It depends totally on the purity of function. So if a function is not pure, you cannot have lazy evaluation.
        In other words, if a function has side effects, i.e before the function call and after the function call
        the outside world changes. Then you are going to ask the question, when did you call that function ?
        Did you call the function before this change or after the change ?
        Impure functions can never be evaluated lazily.
         */

        // Pure Function
        /*
        Pure function returns the same result any number of times we call it with the same input. This is called idempotency
        Pure function do not depend on anything that may possibly change.
        This means you cannot have lazy evaluation if you do not have immutability.
         */

        /*
        In case of Java, life is impure,
        Take an example of Haskell, everything is pure, everything is immutable. If you want to make something impure
        you have to beg and pleade to allow impurity. In case of Java, everything is impure so you have to beg and pleade
        Java to make something pure. In case of Java it is our responsibility to make function pure, compiler will
        not tell us that this is the wrong thing to do.
         */

        // get the list of names in uppercase, of those, who are older than 30
        List<String> namesOlderThan30 = new ArrayList<>();

        // Don't do this
        createPeople().stream()
                .filter(p -> p.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .forEach(name -> namesOlderThan30.add(name)); // This is not a pure function because its mutating
                // And the worst kind of mutability is shared mutability

       // One day developer decides to make the above code parallel
        createPeople().parallelStream()
                .filter(p -> p.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .forEach(name -> namesOlderThan30.add(name));
        //This code might fail because of the shared mutability

        List<String> namesOlderThan30A = createPeople().stream()
                .filter(p -> p.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .reduce(
                        new ArrayList<String>(),
                        (names, name) -> {
                            names.add(name);
                            return names;
                        },
                        (names1, names2) -> {
                            names1.addAll(names2);
                            return names1;
                        }
                );
        /*
        reduce function says, we will start with 3 things.
        1. Empty ArrayList - This becomes my initial value
        2, Given the names collection and name,
        3. how do we deal with collecting sub-objects when running code in parallel. I'm going to combine little collections
        into bigger collection.

        Take an example of a room. We want perform reduce() operation on the room. We will be taking one person at
        a time and sweeping the entire room. This is going to be relatively slow. We are going to divide this room
        in rows;. We will perform reduce() operation on each row parallelly.

        This line : names.add(name); and this : names1.addAll(names2); is mutable. Now the question arises if reduce
        function is pure function ?
        Remember - Purity and impurity is not solely about mutability. It is really about side-effects.
        It is about local mutability. Its internal mutability and you are nor changing shared global variable.
        The above code will work just fine in case of parallel stream. But writing such reduce function becomes complex.
         */

        List<String> namesOlderThan30B = createPeople().stream()
                .filter(p -> p.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        /*
        When it comes to functional pipeline, it becomes our responsibility to make the functions pure.
        If we make the functions impure, we will not be able to perfrom lazy evaluation.
         */

        //name as key and age as value
        Map<String, Integer> nameAndAge = new HashMap<>();
        for(Person person : createPeople()) {
            nameAndAge.put(person.getName(), person.getAge());
        }

        /*
        This is purely imperative style code. Imperative style code is what we are very familiar with.
        Since we are familiar with imperative style code, we can write it fairly eaisly but functional
        style code removes garbage variables, removes additional complexity and its easier to parallelize as well.
         */

        Map<String, Integer> nameAndAgeA =  createPeople().stream()
                //.collect(toMap(keyFunction, valueFunction))
                //.collect(Collectors.toMap(person -> person.getName(), person -> person.getAge()))
                .collect(Collectors.toMap(Person::getName, Person::getAge));


        // get the list of all age value
        List<Integer> ages = createPeople().stream()
                .map(Person::getAge)
                .collect(Collectors.toList());

        // What if we care about immutability so that the above list cannot be modified
        // Similar to toUnmodifiableList, we have toUnmodifiableSet and toUnmodifiableMap
        List<Integer> agesUnmodifiable = createPeople().stream()
                .map(Person::getAge)
                .collect(Collectors.toUnmodifiableList());

        // create comma separated names in UPPERCASE of people older than 30
        // imperative code is built with accidental complexity :)
        // We can do the above exercise using imperative code
        StringBuilder names = new StringBuilder();
        for(Person person : createPeople()) {
            if(person.getAge() > 30) {
                names.append(person.getName().toUpperCase()).append(",");
            }
        }
        String singleString = names.toString();
        singleString = singleString.replaceAll(",$", "");
        // The term "immutable string" in Java refers to a string object that cannot be altered, but the reference
        // to the object can be changed. Every time we make a modification, a new instance of that string is created
        // and the previous value is copied to the new String with the change.
        // this code adds additional comma in the end. Need to write extra code to remove comma
        // More garbage collection in this case

        //functional style
        String commaSeparatedNames = createPeople().stream()
                .filter(p -> p.getAge() > 30)
                .map(Person::getName)
                .map(String::toUpperCase)
                .collect(Collectors.joining(", "));

        // public interface Collector<T, A, R> {
        /*
        The Collector contains 3 parametric types
        It works with 3 different types
        T - Input object that you are dealing with
        A - Accumulator - the mutable accumulation type of the reduction operation
        R - the result type of the reduction operation

        So basically, we start with a collection, accumulate values and then send result
         */

        // PROBLEM - I want to split collection of people into even and odd number age
        List<Person> evenAge = createPeople().stream()
                .filter(person -> person.getAge() % 2 == 0)
                .collect(Collectors.toList());

        List<Person> addAge = createPeople().stream()
                .filter(person -> person.getAge() % 2 != 0)
                .collect(Collectors.toList());

        // We ended up iterating over the collection twice.
        // Imperative style coding friends could argue that we can iterate over the collection once
        // and filter odd and even

        Map<Boolean, List<Person>> evenOddAge = createPeople().stream()
                .collect(Collectors.partitioningBy(person -> person.getAge() % 2 ==0));

        // What If we want to partition into multiple groups, lets say group the people based on their name
        // All the Sara in one group, all the Bob in one group, all the Jack in one group etc.
        Map<String, List<Person>> byName = new HashMap<>();
        for(Person person : createPeople()) {
            List<Person> personList = null;
            if(byName.containsKey(person.getName())) {
                personList = byName.get(person.getName());
                personList.add(person);
            } else {
                personList = new ArrayList<>();
                personList.add(person);
                byName.put(person.getName(), personList);
            }
        }
        byName.forEach((k, v) -> {
            System.out.println("Key is : "+k+ " Value is : "+v);
        });

        // The above code is scary. We would rather do it in functional way
        Map<String, List<Person>> byNameA = createPeople().stream()
                .collect(Collectors.groupingBy(person -> person.getName()));

       // Collector groupingBy -> for each person group by name
        /*
        groupingBy says, when you are given one person at a time create buckets
        Imagine you have empty buckets to begin with
        When a Person is given, you look at the Person's name Sara you check if you have a bucket name labelled as Sera
        If you don't have Sara bucket then it labels the bucket as Sara and drops the Person object into that bucket
        The next Person is Sara, O there is a Sara bucket, drops the object into the same bucket.
        Essentially grouping By is creating bucket
         */

        // We can use method reference in the code above. Visualizing it as buckets we have to assign bucket title
        Map<String, List<Person>> byNameAA = createPeople().stream()
                .collect(Collectors.groupingBy(Person::getName));
        //Next problem : We don't want bucket full of person we want bucket full of age of the Person
        /*
        We cannot perform map after the steam. Once we perform map we do not have Person object, we rather have
        Stream of name. So we have transformed Stream<Person> to Stream<Age/Integer>
        So, we do not want to perform map operation on the stream, we want to perform map operation in the middle of reduce
        */

        /*
        groupingBy(Function<T, R>) ==> Collector : groupingBy takes Function as parameter and returns a Collector
        groupingBy(Function<T, R>, Collector) ==> Collector : overloaded groupingBy takes Function as parameter
        and it also takes second argument which itself is a Collector. This blows my mind because its a Collector that
        can recursively use another Collector
        */

        Map<String, List<Integer>> byNameAAA = createPeople().stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.mapping(Person::getAge, Collectors.toList())));

        //If you look at the earlier groupingBy code, you would notice that it assumes that you need a List.
        // We have never specified Liat as return type.
        //When we use Collectors.mapping inside groupingBy, we need to specify target though. Collectors.mapping
        // is not going to assume the target.

        //What if we want Set of ages. If we just want unique age
        Map<String, Set<Integer>> byNameAAAA = createPeople().stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.mapping(Person::getAge, Collectors.toSet())));

        // Problem : You want to count number of freinds i.e group the friends by name and count number

        Map<String, Long> countByName = createPeople().stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.counting()));

        // counting collector function returns a Long. But we want integer
        //In this case both groupingBy and mapping is a Collector
        // They took a Function as first argument and then they took a Collector as a second argument
        // groupingBy and map (Function, Collector)
        //now, counting itself is a collector and once you count you want to go from a Long to Integer
        // In other words, you want opposite of above i.e arguments should be (Collector, Function)
        // collectingAndThen(Collector, Function)


        Map<String, Integer> countByNameA = createPeople().stream()
                .collect(Collectors.groupingBy(Person::getName,
                        Collectors.collectingAndThen(Collectors.counting(), value -> value.intValue())));

        Map<String, Integer> countByNameAA = createPeople().stream()
                .collect(Collectors.groupingBy(Person::getName,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));

        // Total of age value
       Integer totalAge1 =  createPeople().stream()
                .map(Person::getAge)
                .reduce(0, (total, age) -> total + age);

        Integer totaoAge2 = createPeople().stream()
                .mapToInt(Person::getAge)
                .sum();
        // reduce takes multiple forms, it take form of coleect, sum, min, max

        OptionalInt maxAGe = createPeople().stream()
                .mapToInt(Person::getAge)
                .max();
        //why OptionalInt in this case, why sum is not returning Optional ?
        // In case of empty collection we do not know min and max value however the sum can still be 0.

        // Problem : max and min functions return int value. What of we are interested in the Person object itself
        // i.e return the Person with maximum age
        Optional<Person> personmaxAge = createPeople().stream().max(Comparator.comparing(Person::getName));

        Optional<Person> personminAge = createPeople().stream().min(Comparator.comparing(Person::getName));

        Optional<Person> personminAgeA = createPeople().stream()
                .collect(Collectors.maxBy(Comparator.comparing(Person::getAge)));

        String personName = createPeople().stream().collect(Collectors.collectingAndThen(
                Collectors.maxBy(Comparator.comparing(Person::getName)), person -> (person.map(Person::getName).orElse(""))));

        // map vs mapping
        // map - when you want to transform stream
        // mapping - you want to transform something when you are in the middle of reduce

        /*
        similarly we have filter vs filtering. In the middle of reduce you can perfrom transformation usring mapping
        similarly, you can perform filtering in the middle of reduce
         */

        Map<Integer, List<Person>> groupPersonByAge = createPeople().stream().collect(Collectors.groupingBy(Person::getAge));
        Map<Integer, List<String>> groupPersonNameByAge = createPeople().stream()
                .collect(Collectors.groupingBy(
                        Person::getAge, Collectors.mapping(Person::getName, Collectors.toList())));

        // rather than toList we can have filtering i.e filter person name based on length
        // not working for some reason
        Map<Integer, List<String>> groupPersonNameByAgeA = createPeople().stream()
                .collect(Collectors.groupingBy(
                        Person::getAge, Collectors.mapping(
                                Person::getName,
                                Collectors.filtering(p -> p.length() > 4, Collectors.toList()
                ))));

        // grouping mapping (Function, Collector)
        // collectingAndThen (Collector, Function)
        // teeing (Collector, Collector, operation)

        // in case of teeing it takes multiple collectors and perfrpms operation on them

        // If I have a for loop, I can write stream API for it. But what if we have for loop inside another for loop ?
        // These kinds of problem are where you are not dealing with collection but you are dealing with a collection
        // that in turn contains a collection

        List<Integer> numbers = List.of(1, 2, 3);
        List<Integer> doubleNumbers =  numbers.stream()
                .map(e -> e * 2) // one to one function. give me e and I will give you 2 times e
                .collect(Collectors.toList());

        // In the above case we have Stream<T>, we applied function on it and we Stream<R>
        // Stream<T>.map(f) --> Stream<R>

        List<List<Integer>> doubleNumbersA =  numbers.stream()
                .map(e -> List.of(e - 1, e - 2))  //one to many
                .collect(Collectors.toList());

        // For every value e, we are returning e-1 and e -2. But the question is is this what we want ?
        // Take an example from Kotlin to understand the meaning of flatten first
        /*
        val numbers = listOf (
            listOf(1, 2),
            listOf(3, 4, 5),
            listOf(6, 6, 8)
        )

        println(numbers.size) -> It is going to print 3 because we have list of list and we are counting number of lists
        inside a list

        prinln(numbers.flatten().size) -> This is going to print 8 because we have flatten list of list. This can be
        visualized as many stack of bokks kept on the floor. We have to flatten each stack and keep all the books
        one the floor. There should not any book kept obn top of other book

        numbers.map {e -> listOf (e-1, e + 1)}
        .flatten()

        Here I have performed a map and then I have performed a flatten. In Java we have flatMap to perform the mapping first
        and flatten the result
         */

        /*
        numbers.stream()
                .flatMap(e -> List.of(e - 1, e - 2))  //one to many
                .collect(Collectors.toList());

        The collection inside flatMap could be anyting. It could be a List, a Set or something else.

        Stream
            .map(Function<T, R>) ==> Stream<R>
            .flatMap(Function<T, ????>)

        If you perform a map on stream, you pass a Function and get a resulting stream
        In case of flatMap, what could be return type ? It coule be List<R> or Set<R> etc

         Stream
            .flatMap(Function<T, List<R>>) ===> Stream<R>

        Stream
            .flatMap(Function<T, Set<R>>)  ===> Stream<R>

        From the compilers point of view, if ???? is going to be all kinds of weired types like List<R>
        Set<R> etc flatMap will not be able to deal with it. So the flatMap does not want a collection
        because you can give any type of collection. It expects an iterator of a collection.

        Note that Stream is a iterator. So the flatMap wants you to give a stream
         */

        List<Integer> numbersFlatten = numbers.stream()
                .flatMap(e -> List.of(e - 1, e - 2).stream())  //one to many
                .collect(Collectors.toList());

        //If you have a one-to-one function, use map to go from
        // Stream<T> to Stream<R>

        //If you have a one-to-many function, use map to go from
        // Stream<T> to Stream<Collection<R>>

        //If you have a one-to-many function, use flatMap to go from
        // Stream<T> to Stream<R>

        // Problem : You want characters in the name

        System.out.println(createPeople().stream()
                .map(Person::getName)
                .flatMap(name -> Stream.of(name.split("")))
                .collect(Collectors.toList()));

      //group Person stream by age. Just group by will give me ourput like this
        // {20 = [Sara -- 20, Jack -- 20], 30 -> [Daniel --30 , Jim -- 30], 32-> [Peter --32, Tim --32]}
        // I want just the name not the Person object
        // {20 ->[Sara, Jack], 30 -> [Daniel, Jim], 32-> [Peter, Tim]}
        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toList()))));


        // What if we want characters in the name

        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(person -> person.getName().split(""),
                        Collectors.toList()))));
        //This is going to return me array of
        // {32=[[Ljava.lang.String;@735f7ae5, [Ljava.lang.String;@180bc464], 3=[[Ljava.lang.String;@1324409e], 20=[[Ljava.lang.String;@2c6a3f77, [Ljava.lang.String;@246ae04d], 22=[[Ljava.lang.String;@62043840], 72=[[Ljava.lang.String;@5315b42e], 11=[[Ljava.lang.String;@2ef9b8bc]}

        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(person -> Stream.of(person.getName().split("")),
                        Collectors.toList()))));
        //I can get the Stream intead of Arrays if I want but this is going to be worst.
        // All I need is flaMapping instead of mapping
        //{32=[java.util.stream.ReferencePipeline$Head@3f2a3a5, java.util.stream.ReferencePipeline$Head@4cb2c100], 3=[java.util.stream.ReferencePipeline$Head@6fb554cc], 20=[java.util.stream.ReferencePipeline$Head@614c5515, java.util.stream.ReferencePipeline$Head@77b52d12], 22=[java.util.stream.ReferencePipeline$Head@2d554825], 72=[java.util.stream.ReferencePipeline$Head@68837a77], 11=[java.util.stream.ReferencePipeline$Head@6be46e8f]}

        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.flatMapping(person -> Stream.of(person.getName().split("")),
                        Collectors.toList()))));
        // {32=[P, a, u, l, a, P, a, u, l], 3=[J, a, c, k], 20=[S, a, r, a, B, o, b], 22=[S, a, r, a], 72=[J, a, c, k], 11=[J, i, l, l]}

        // Just give me unique letters in the name. We can use toSet
        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.flatMapping(person -> Stream.of(person.getName().split("")),
                        Collectors.toList()))));

        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.flatMapping(person -> Stream.of(person.getName().split("")),
                        Collectors.toSet()))));


        //Lambdas expressions should be glue code. Two lines may be too many.
        // The moment we add curly braces in our lambda our code becomes un-readable.
        // I can put the code in a method and the method can be invoked from lambda.
        // By properly modularizing the code I can unit test methods seperately.

        // Exception handling is an imperative style of programming concept.
        // In functional programming we deal with Stream of data -> This is basically data flow
        // In imperative programming func1() calls func2(), this then calls func3(). If something goes
        // wrong in func3(), you blow up your stack.

        // Crude analogy - A friend of you calls you and says - I'm driving on a freeway and I've a flat tyre, what should I do ?
        // Suggestion - You have a flat tyre, drive reverse and go back where you came from.
        // This is wrong suggestion, what you can say instead is exit safely and go to the nearest place where you can
        // get help
        // Handle the exception in downstream -> This is what happens in functional programming
        // Exception handling makes zero sense when it comes to functional programming

        // A great source of inspiration is reactive library. They give you 3 chaneels
        // Data channel, error channel, complete channel.
        // The data channel carries the data and when something goes wrong, the error channel carries error.

        // You want to design your code in such a way that data is data and error is also data.
        // They are just 2 different forms of data.
        // You take your pipeline, perform filtering, transformation/mapping, if everything goes well
        // you go downstream. If something goes wrong, you still go to the next stage but you go with the error.
        // The next stage says this is error and I don't have anyting to handle. I'm going to pass it down and down
        // until I get to the end and you can report your errors at that point.
        //


        // Sorting
        //If 2 people have same age then compare based on name
        createPeople().stream()
                .sorted(Comparator.comparing(Person::getName).thenComparing(Person::getAge))
                .forEach(System.out::println);


















    }
}
