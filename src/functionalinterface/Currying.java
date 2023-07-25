package functionalinterface;

import java.util.Objects;
import java.util.Scanner;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Currying {
    public static void main(String[] args) {
        //multiplying 2 numbers
        BiFunction<Integer, Integer, Integer> multiply = (n1, n2) -> n1 * n2;
        System.out.println(multiply.apply(10, 20));

        // multiplying 2 numbers using Function
        Function<Integer, Function<Integer, Integer>> multiplyI = n1 -> n2 -> n1 * n2;
        System.out.println(multiplyI.apply(10).apply(20));

        // Evaluating Evaluating 2x+3y+5z
        Function<Integer, Function<Integer, Function<Integer, Integer>>> expression = x -> y -> z -> 2*x + 3*y +  5*z;
        System.out.println(expression.apply(2).apply(3).apply(5));

        // Curried Function for computing simple interest
        // S.I = P * R * T /100   Principal - P, Rate - R, Time - T
        Function<Double, Function<Double, Function<Double, Double>>> SI = P -> R -> T -> (P * R * T) /100;

        SI o= new SI("Savings");
        // Getting principal and number of years from the user
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter your principal amount ");
        double p=sc.nextFloat();
        System.out.println("Enter the duration of deposit in Years");
        double n=sc.nextFloat();

        // Calling Curried Function for computing simple interest
        System.out.println("Simple Interest on principal "+p+" with rate "+o.getrate()+" for "+n+" years will be "+ SI.apply(p).apply(o.getrate()).apply(n));


    }

    static class SI {
        double rate;
        SI(String t)
        {
            if(Objects.equals(t, "Savings"))
                this.rate=7.5f;
            else
                this.rate=3.5f;
        }
        public double getrate()
        {
            return this.rate;
        }
    }
}
