package vlad.functional.heaven.maybe;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public final class Just<A> extends Maybe<A> {
    private final A value;

    Just(A value) {
        this.value = requireNonNull(value);
    }

    public A value() {
        return value;
    }

    @Override
    public <B> B cast(Function<Nothing<A>, B> nothing, Function<Just<A>, B> just) {
        return just.apply(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Just<?> just = (Just<?>) o;

        return value.equals(just.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
