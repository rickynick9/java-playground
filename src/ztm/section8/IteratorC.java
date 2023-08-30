package ztm.section8;

import java.util.Objects;
import java.util.function.Consumer;

public interface IteratorC<E> {

    boolean hasNext();
    E next();
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}
