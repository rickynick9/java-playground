package functionalinterface;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/*

T -- f --> R
R -- g --> V
andThen composition
T --fog --> V


V -- gof --> R

V -- g --> T
T -- f --> R

*/

/*
The functional interface Function<T, R> has two default methods compose and andThen for composing new functions.
The main difference between these methods lies in the execution order.
Generally, f.compose(g).apply(x) is the same as f(g(x)), and f.andThen(g).apply(x) is the same as g(f(x)).
 */
public interface GenericInterface<T, R> {
    R func(T t);

    /*
    generic method declarations have a type parameter section delimited by angle brackets (< and >)
    that precedes the method's return type.

    Each type parameter section contains one or more type parameters separated by commas.
    A type parameter, also known as a type variable, is an identifier that specifies a generic type name.

    The type parameters can be used to declare the return type and act as placeholders for the types of the arguments
    passed to the generic method, which are known as actual type arguments.

    The type parameters can be used to declare the return type, parameter types and local variable types in a
    generic method declaration, and they act as placeholders for the types of the arguments passed to the generic
    method, which are known as actual type arguments.

    A nongeneric class doesn’t define type parameters. To define a generic method in a nongeneric class or
    interface, you must define the type parameters with the method, in its type parameter section.

    You can also use more than one type parameter in generics in Java, you just need to pass specify
    another type parameter in the angle brackets separated by comma.

     */
    default <V> GenericInterface<T, V> andThen(GenericInterface<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.func(func(t));
    }

    //f(g(x)) ->

    default <V> GenericInterface<V, R> compose(GenericInterface<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> func(before.func(v));
    }


}
