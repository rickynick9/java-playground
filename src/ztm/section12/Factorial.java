package ztm.section12;

public class Factorial {

    private int factorialIterative(int n) {
        int factorial = 1;
        for(int i =n; i>0; i--) {
            factorial *=i;
        }
        return factorial;
    }

    // 5! = 5 * 4 * 3 * 2 * 1
    // 5! = 5 * 4!
    // 4! = 4 * 3!
    private int factorialRecursive(int n) {
        if(n == 1)
            return n;
        return n * factorialIterative(n-1);
    }

    public static void main(String[] args) {
        Factorial factorial = new Factorial();
        System.out.println(factorial.factorialIterative(5));
        System.out.println(factorial.factorialRecursive(5));
    }
}
