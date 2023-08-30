package designpatterns;

/*

Not to jump on a design pattern but to arrive at a design pattern as code evolves.
Not force a design pattern into your code, it often leads to complexity.
Forcing a design pattern into your code is like walking around with a hammer and asking can you show me mail so that I can hit


Optional - Patterns and anti-patterns

Null is a smell and we really want to avoid null in our code as much as possible.
A good design reads like a story and not a puzzle
I really like code that does not make me think
Make the code obvious

Effective Java : Do not return a null, instead return an empty *collection*

What if we have a single value ?
In the past we returned null.
Now we should return Optional<T>


 */

import java.util.Optional;

public class OptionalPattern {

    public static String getName() {
        // We do not have any name, so return null
        return null; // Bad Idea
    }

    public static Optional<String> getNameO() {
        if(Math.random() > 0.5) {
            return Optional.of("Joe");
        }
        return Optional.empty();
    }

    public static void setName(String name) {
        if(name == null) { //smell
            //Use default name Joe
        } else {
            // use the given name
        }
    }

    public static void setNameA(Optional<String> name) {
        if(name.isPresent()) {
            // use the name
        } else {
            // Use default name Joe
        }
    }
    //The above code is a also a bad idea. A good design should have empathy
    // The caller of this method is punished everytime when they call this function.
    // Caller have to call in this way
    // setNameA(Optional.empty())
    // setName(Optional.of("Sars"))

    public static void setNameB() {
        // use default value Joe
    }

    public static void setNameB(String name) {
        // use the given name
    }
    //Above code is better because callee can invoke setName() if they do not have any name else
    // they can call setName("Sara")

    /*
    Advice : Do not use Optional<T> as a parameter to methods
    If needed, use overloading instead
     */

    public static void main(String[] args) {
        var result = getNameO();
        System.out.println(result);
        System.out.println(result.orElse("Not Found"));
        //please don't do this : result.get()
        // get will blow up if result is empty and that defeats the whole purpose of Optional
        // The problem with get is it does not reveal its intention
        // The JDK developers should have really called get as willBlowUpIfNotPresent()
        // This would have been a very clear intention. In fact JDK developers gave a name orThrow()
        // This is a lot better

        //If at all you need to use get() use orThrow() instead
        System.out.println(result.orElseThrow());

        /*
        If a method will always have a single value as a result please *DO NoT Use Optional*

        If a method may or mat not have single value as a result then use Optional

        If the result is a collection, then don't use Optional. You can always return an empty collection as
        List.of()
        Makes no sense to use Optional of collection.

         */


    }

}
