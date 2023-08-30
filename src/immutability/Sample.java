package immutability;

// The Power and Practicality of Immutability by Venkat Subramaniam
// Immutability is certainly the opposite of mutability and mutability is when we modify the states of certain objects.


/*
Mutability is something that we do quite often but why should we be really concerned about it ?

Mutability is OK, sharing is good but shared mutability is devil's work.
The moment we bring-in shared mutability, it comes really difficult for us to work with concurrency in code.

Functional programming - You should program with immutability, they talk about pure functions.
We should do assignment less programming. If we take an example of Erlang or Haskell there is no assignment in those
languages.


 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Sample {
    public static void main(String[] args) {
        for(int i =0; i< 10; i++) {
            if(i > 5) {
                System.out.println(i);
            }
        }

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // Total all even numbers in this collection

        int total = 0;
        for(int i =0; i< numbers.size(); i++) {
            if(numbers.get(i) % 2 == 0) {
                total += numbers.get(i);
            }
        }
        System.out.println(total);

        // We are doing lots of mutation in this code. This poor variable i is no a constant. We keep changing this
        // variable throughout the loop.
        // We are constantly changing variable total as well.

        System.out.println(
                numbers.stream()
                        .filter(n -> n % 2 == 0)
                        .mapToInt(n -> n)
                        .sum()
        );

        // This functional style code does not have any explicit mutability.
        // This code is very humane because no variable was tortured to solve this problem :)
        // As in imperative code variable total was continuously modified and tortured.
        // It is of to perform mutability under the hood if the under the hood source is trusted and they
        // take the responsibility of making the mutability localized and making sure concurrency is not affected.

    // Common anti-pattern people run into in functional programming
    /*
    Problem : I want to double the even values and put the values into a collection
     */

    List<Integer> doubled = new ArrayList<>();
    numbers.stream()
            .filter(n -> n % 2 ==0)
            .map(n -> n * 2)
            .forEach(n -> doubled.add(n)); // Terrible programming practice

    // "It works" is a terrible choice of words when it comes to programming. We should rather say, the program behaves
    // The above program behaves in certain way but the moment we turn it into parallel, we cannot predict its behaviour

        List<Integer> doubledD = numbers.stream()
            .filter(n -> n % 2 == 0)
            .map(n -> n * 2)
            .collect(toList());
    }
}
//Nothing really exciting in this program, we have if structure, for structure etc.
// But if we look at the byte code, there is a beautiful surprise waiting for us in byte code, its goto keyword
// goto is like matches, everyone has matches in home. If you go to take a shower, you don't ask your children to
// play with matches. That's the exact point, goto is for adults to deal with and not for us to deal with on daily basis.
// goto is to structured programming as assignment is to functional programming
/*
 The addition of unstructured jumps makes it much more challenging to trace through a program.
 The #1 reason may be that it is possible to use goto in such a manner that it obscures the structure of a program
 to the point where it becomes impossible to decipher.

We do agree that we should not be using goto in structured programming but that does not mean we do not have any goto
as we saw in our bytecode. In a similar way when we are writing assignment-less functional code, it does not mean
no assignment at all, it simply means we don't do assignment in our code but there could be assignment/mutations in
layers below code.

If we are mutating in our code, reasoning the code becomes really hard.
Understanding the code becomes hard
Maintaining the code becomes hard
Making the code concurrent really hard

If you call a functional library, and if they mutability then reasoning the code is not your problem
it becomes their problem, maintaining the code is not your problem it is their problem,
making the code concurrent is not your problem, it is their problem.
In life you want to make everything other people's problem :)

Explicit mutability should be avoided. It could happen in controlled fashion in layers below.

Refer the example above for total value in a collection with and without mutability.

PURE FUNCTIONS : returns the same result for same input no matter how many times you call it
Pure functions have no side effects

Two rules of purity :
    1. The function does not mutate any state
    2. The function does not depend on anything that may possibly change

Pure function sees no evil and does no evil (Here I consider mutability to be evil)
It's not enough that you don't do evil, you should not even see evil.

So pure function essentially means, you don't want to mutate something and you also do not want to depend on
something that can potentially be mutated.

Benefits of immutability :
1. Opens door to reason with the code very well : It you go through a code that is so complex and mutating every
single corner you turn, you try to go up and down in this function try to map all variables with and states.

2. Easy to understand
3. Easy to explain
4. Easy to test : When a function that you call is impure, you have to depend on mock, stub etc.
If a function is pure, you don't need to do any stinking mock object.

No stub or mock needed if functions are pure

 */

/*
javac Account.java

javap -c Account.class
Compiled from "Account.java"
public class immutability.Account {
  public immutability.Account();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: iconst_0
       1: istore_1
       2: iload_1
       3: bipush        10
       5: if_icmpge     26
       8: iload_1
       9: iconst_5
      10: if_icmple     20
      13: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
      16: iload_1
      17: invokevirtual #3                  // Method java/io/PrintStream.println:(I)V
      20: iinc          1, 1
      23: goto          2
      26: return
}


 */
