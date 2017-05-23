package vlad.functional.heaven;

import vlad.functional.heaven.function.BiFunction;
import vlad.functional.heaven.function.Function;
import vlad.functional.heaven.higher_order.Holed;

import static java.util.Objects.requireNonNull;

public final class Tuple<A, B> implements Holed<Tuple<A, ?>, B> {
    private final A first;
    private final B second;

    private Tuple(A first, B second) {
        this.first = requireNonNull(first);
        this.second = requireNonNull(second);
    }

    public static <A, B> Tuple<A, B> tuple(A first, B second) {
        return new Tuple<>(first, second);
    }

    public static <A, B> Tuple<A, B> resolve(Holed<Tuple<A, ?>, B> holed) {
        return (Tuple<A, B>) holed;
    }

    public A first() {
        return first;
    }

    public B second() {
        return second;
    }

    public <C> C match(BiFunction<A, B, C> f) {
        return f.apply(first, second);
    }

    public <C> Tuple<A, C> map(Function<B, C> f) {
        return tuple(first, f.apply(second));
    }

    public <C> Tuple<C, B> mapFirst(Function<A, C> f) {
        return tuple(f.apply(first), second);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple<?, ?> tuple = (Tuple<?, ?>) o;

        if (!first.equals(tuple.first)) return false;
        return second.equals(tuple.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }
}
