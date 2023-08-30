package ztm.section8.queue;

import java.util.Objects;
import java.util.function.Predicate;

public interface CollectionI <E> extends IterableI<E> {

    int size();
    boolean isEmpty();
    IteratorI<E> iterator();
    boolean add(E e);

    boolean addAll(CollectionI<? extends E> c);
    boolean removeAll(CollectionI<?> c);
    boolean retainAll(CollectionI<?> c);
    void clear();

    default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean removed = false;
        final IteratorI<E> each = iterator();
        while (each.hasNext()) {
            if (filter.test(each.next())) {
                each.remove();
                removed = true;
            }
        }
        return removed;
    }


}
