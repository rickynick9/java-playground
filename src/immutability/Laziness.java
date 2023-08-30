package immutability;

import java.util.Arrays;
import java.util.List;

public class Laziness {

/*
Immutability makes laziness possible
 */
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9, 10);

        // Problem : Find the double of the first number > 3 and is even
        int result = 0;
//        for(int e: numbers) {
//            if(isGT3(e) && isEven(e)) {
//                result = doubleIt(e);
//                break;
//            }
//        }
        System.out.println(result);

        System.out.println(numbers.stream()
                .filter(Laziness::isGT3)
                .filter(Laziness::isEven)
                .map(Laziness::doubleIt)
                .findFirst()
                .orElse(0)
        );
        //Functional style code is absolutely lazy in execution.
        // Every operation like filter, map etc is not going to create new collection
        // Because more garbage we create, more garbage we have to collect
        // If we have a very large collection then it would not be good in performance.
        // Thankfully we have purity of functions and it leads to lazy evaluation.
        // As we could see, the performance of functional and imperative style code is same as far as function invocation is
        // concerned.




    }

    public static boolean isEven(Integer number) {
        System.out.println("isEven called for :"+number);
        return number % 2 == 0;
    }
    public static boolean isGT3(Integer number) {
        System.out.println("isGT3 called for :"+number);
        return number > 3;
    }
    public static Integer doubleIt(Integer number) {
        System.out.println("doubleIt called for :"+number);
        return number * 2;
    }

    /*
    We could see this o/p after executing the imperative code.
        isGT3 called for :1
        isGT3 called for :2
        isGT3 called for :3
        isGT3 called for :5
        isEven called for :5
        isGT3 called for :4
        isEven called for :4
        doubleIt called for :4
     */
}
