package ztm.section8.queue;


import java.util.Objects;

import java.util.function.Consumer;

/*
It represents a data structure that can be iterated over.
All collections in Java implement the Iterable interface.

Iterate Over an Iterable : To iterate over iterable, we can use enhanced for loop or it can also be iterated using
Iterator.

 */
public interface IterableI<T> {
    IteratorI<T> iterator();

    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        //for (T t : this) {
            //action.accept(t);
        //}
    }


}
