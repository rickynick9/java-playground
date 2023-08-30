package ztm.array;

import java.util.Objects;
import java.util.function.IntConsumer;

public interface IntIterator {

    boolean hasNext();

    int next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(IntConsumer consumer) {
        Objects.requireNonNull(consumer);
        while (hasNext())
           consumer.accept(next());
    }
}
