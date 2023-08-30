package ztm.section8.queue;

import java.util.function.Consumer;

public interface IteratorI<E> {

    boolean hasNext();

    E next();

    default void forEachRemaining(Consumer<E> c) {
        while (hasNext())
            c.accept(next());
    }

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
