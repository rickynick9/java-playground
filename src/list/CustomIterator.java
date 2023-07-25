package list;

import java.util.Objects;
import java.util.function.Consumer;

public interface CustomIterator<E> {

    E next();

    boolean hasNext();

    /*
    Removes from the underlying collection the last element returned by this iterator (optional operation).
    This method can be called only once per call to next.
     */
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext()) {
            action.accept(next());
        }
    }

}
