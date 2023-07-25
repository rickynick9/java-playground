package list;


import java.util.Objects;
import java.util.function.Consumer;

public interface CustomIterable<T> {

    /*Returns an iterator over elements of type T.*/
    CustomIterator<T> iterator();

    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
//        for (T t : this) {
//            action.accept(t);
//        }
    }

}


