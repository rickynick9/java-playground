package list;

import java.util.Objects;
import java.util.function.Predicate;

/*

The root interface in the collection hierarchy. A collection represents a group of objects, known as its elements.
Some collections allow duplicate elements and others do not. Some are ordered and others unordered.




 */

public interface CustomCollection<E> extends CustomIterable<E> {

    int size();

    boolean isEmpty();

    boolean add(E e);

    boolean remove(E e);

    boolean addAll(CustomCollection<?> cc);

    boolean removeAll(CustomCollection<?> cc);

    /*
    Removes all of the elements of this collection that satisfy the given predicate.
     */
    default boolean removeIf(Predicate<? super  E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final CustomIterator<E> iterator = customIterator();
        while (iterator.hasNext()) {
            if(filter.test(iterator.next())) {
                iterator.remove();
                removed = true;
            }
        }
        return removed;
    }
    CustomIterator<E> customIterator();

    void clear();
}
