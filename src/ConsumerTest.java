import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

public class ConsumerTest {

    public static void main(String[] args) {
        Consumer<List<Integer>> listConsumer =  (list) -> {
            for (int i=0; i< list.size(); i++) {
                List<Integer> copy = list;
                copy.set(i, 2 * list.get(i));
            }
        };

        List<Integer> arrayList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        listConsumer.accept(arrayList);
        arrayList.forEach(e -> System.out.println(e));

        /*=======Even Odd List============= */
        List<Integer> evenList = new ArrayList<>();
        List<Integer> oddList = new ArrayList<>();
        List<Integer> originalList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        Consumer<List<Integer>> evenOddConsumer = (list) -> {
            for (int i=0; i< list.size(); i++) {
                if(list.get(i) % 2 == 0)
                    evenList.add(list.get(i));
                else
                    oddList.add(list.get(i));
            }
        };

        Consumer<List<Integer>> printList = (list) -> {
            list.forEach(System.out::println);
        };

        evenOddConsumer.accept(originalList);

        System.out.println("Even List :\n");
        printList.accept(evenList);
        System.out.println("Odd List :\n");
        printList.accept(oddList);


        List<String> musketeers = new ArrayList<String>();
        musketeers.add("D'Artagnan");
        musketeers.add("Athos");
        musketeers.add("Aramis");
        musketeers.add("Porthos");

        Consumer<List<String>> upperCaseConsumer = (list) -> {
            for(int i=0; i< list.size(); i++) {
                list.set(i, list.get(i).toUpperCase());
            }
        };

        Consumer<String> say = (s) -> {
            System.out.println("Hello "+s+ " !");
        };

        Consumer<List<String>> sayAll = list -> list.stream().forEach(say);
        upperCaseConsumer.andThen(sayAll).accept(musketeers);

        /* Consumer update and display Map data */

        Map<Integer, String> persons = new HashMap<Integer, String>();
        persons.put(101, "Mahesh");
        persons.put(102, "Krishna");

        Consumer<Map<Integer, String>> updatePersons = Utility::updateData;
        Consumer<Map<Integer, String>> displayPersons = Utility::displayData;

        updatePersons.andThen(displayPersons).accept(persons);
    }

    static class Utility {
        static void updateData(Map<Integer, String> people) {
            people.replaceAll((k, v) -> "Shree ".concat(v));
        }

        static void displayData(Map<Integer, String> persons) {
            for (Map.Entry<Integer, String> entry : persons.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue());
            }
        }

    }

}
