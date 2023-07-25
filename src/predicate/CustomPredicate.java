package predicate;

import java.util.Objects;

public interface CustomPredicate<T> {

    boolean test(T t);

    default CustomPredicate<T> and(CustomPredicate<T> other) {
        Objects.requireNonNull(other);
        return (T t) -> test(t) && other.test(t);
    }

    default CustomPredicate<T> or(CustomPredicate<T> other) {
        Objects.requireNonNull(other);
        return (T t) -> test(t) || other.test(t);
    }

}
