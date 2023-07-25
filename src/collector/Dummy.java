package collector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dummy {

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
            System.out.println("Key is : "+k);
            v.forEach(person -> {
                System.out.println("Value is :"+person.getName());
            });
        });

        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(Person::getName, Collectors.toList()))));

        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(person -> person.getName().split(""),
                        Collectors.toList()))));

        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.mapping(person -> Stream.of(person.getName().split("")),
                        Collectors.toList()))));

        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.flatMapping(person -> Stream.of(person.getName().split("")),
                        Collectors.toList()))));

        System.out.println(createPeople().stream()
                .collect(Collectors.groupingBy(Person::getAge, Collectors.flatMapping(person -> Stream.of(person.getName().split("")),
                        Collectors.toSet()))));

    }
}
