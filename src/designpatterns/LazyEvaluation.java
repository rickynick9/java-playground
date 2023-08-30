package designpatterns;

public class LazyEvaluation {

    /*
    https://www.youtube.com/watch?v=yTuwi--LFsM&t=2991s

    https://www.youtube.com/watch?v=2B4Zt4hBG9Y

    https://www.youtube.com/watch?v=nVZE53IYi4w
    https://www.youtube.com/watch?v=bs8oXGOnTzw
    https://www.youtube.com/watch?v=ekFPGD2g-ps
    https://www.youtube.com/watch?v=1OpAgZvYXLQ&t=19s
    https://www.youtube.com/watch?v=0hQvWIdwnw4

    https://www.youtube.com/results?search_query=Venkat+Subramaniam

    https://www.youtube.com/watch?v=kG2SEcl1aMM
    
     */


    /*
    Lazy evaluation is to functional programming as polymorphism is to object-oriented programming.
    Without laziness all we are going to get is a cute looking code but that is not going to perform well.
    Lazy evaluation becomes extremely critical for us to benefit from functional programming.

    How can be embrace laziness ?
    Laziness is already implemented in stream() API. But what if we want to do something similar in our code ?

    We are going to write some code that is going to have some computational complexity.

     */

    public static int compute (int number) {
        // Imagine it takes some time to compute
        System.out.println("Compute called ....");
        return number * 100;
    }

    public static void main(String[] args) {
        int value = 4;

        if(value > 4 && compute(value) > 100) {
            System.out.println("PATH 1");
        } else {
            System.out.println("PATH 2");
        }

        System.out.println("Another Way....");

        int temp = compute(value);
        // This is called eager evaluation where function is invoked eagerly and it doesn't wait to see if you are
        // going to use the result or not. This is the nature of Java and eager evaluation is the default behaviour


        if(value > 4 && temp > 100) {
            System.out.println("PATH 1A with temp value : "+temp);
        } else {
            System.out.println("PATH 2A");
        }
    }

    //short circuiting - we evaluated first if condition and since it was false compiler did not bother jumping into
    // another branch i.e compute function

    /*
    David Wheeler : In CS we can solve almost any problem by introducing one more level of indirection.

    In procedural code, pointers give power of indirection.
    In computer programming, indirection (also called de-referencing) is the ability to reference something using a name,
    reference, or container instead of the value itself. The most common form of indirection is the act of manipulating
    a value through its memory address.

    In object-oriented code overriding functions give the power of indirection. Function you are calling is not decided
    at run time, but it is called at run time. This gave us power of indirection.

    In Function Programming, lambdas give power of indirection.
    Lambdas gave you an ability to postpone a call

    You can call the lambdas right away or you can save the lambdas for later use.
    You can pass the lambdas to another function.
    Lambdas give you power of deferring evaluation to a later time







     */

}
