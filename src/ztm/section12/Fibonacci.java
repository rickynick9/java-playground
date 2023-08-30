package ztm.section12;

public class Fibonacci {
    /*
    0, 1, 1, 2, 3, 5, 8, 13, 21, 38, . . .
    In a Fibonacci series, every term is the sum of the preceding two terms

    f(0) = 0
    f(1) = 1
    f(2) = 0 + 1 = 1
    F(3) = 1 + 1 = 2
    f(4) = 1 + 2 = 3
    f(5) = 2 + 3 = 5

    Fn = Fn-1 + Fn-2, where n > 1
     */

    public static void main(String[] args) {

        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.fibonacci(8));
        System.out.println(fibonacci.fibonacciIterative(8));

    }

    private int fibonacciIterative(int n) { // O(n)
        //initialize array with first 2 fibonacci numbers
        int[] arr = new int[20];
        arr[0] = 0; arr[1] = 1;

        for(int i=2; i<=n; i++) {
            arr[i] = arr[i-1] + arr[i-2];
        }
        return arr[n];
    }

    /*
    Check fibonacci tree image. the size of tree grows exponentially when n increases
    O(2^n)

    Why do we ever want to use recursion ?
    With the help of memoization we can reduce the time complexity of recursive approach to O(n)

    recursive code allows your code to be DRY

    Recursion pros : DRY code, Readability
    Cons : Extra memory footprint, large call stack
     */
    private int fibonacci(int n) {
        if(n == 0)
            return 0;
        else if(n == 1)
            return 1;

        /*
        if(n < 2) return n;
         */

        return fibonacci(n-1) + fibonacci(n-2);
    }
}
