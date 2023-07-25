package functionalinterface;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HOF {

    public static void main(String[] args) {
        List<Integer> arrayList = Arrays.asList(1,3,5,7,9);
        Function<Integer, Integer> doubler = (v) -> v * 2;
        List<Integer> doubled = map(arrayList, doubler);
        doubled.forEach(System.out::println);

        System.out.println("\n");

        List<Integer> arrayList1 = Arrays.asList(1,3,5,7,9,10,11,12,13,14,15,15);
        Predicate<Integer> predicate = (e) -> e % 2 == 0;
        List<Integer> even = filter(arrayList1, predicate);
        even.forEach(System.out::println);

        //group by string length
        List<String> in = Arrays.asList("A", "AB", "B", "ABC");
        final Map<Integer, List<String>> all = in.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println(all);

    }

    private static <T, R> List<R> map(List<T> list, Function<? super T, ? extends R> func) {
        List<R> result = new ArrayList<>();
        for(T element: list) {
            result.add(func.apply(element));
        }
        return result;
    }
    //another higher order function to test each element in array and filter out even
    private static <T, R> List<R> filter(List<T> list, Predicate<T> predicate) {
        List <R> result = new ArrayList<>();
        for(T element : list) {
            if(predicate.test(element))
                result.add((R) element);
        }
        return result;
    }
}
