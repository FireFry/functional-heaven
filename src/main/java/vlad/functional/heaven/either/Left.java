package vlad.functional.heaven.either;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

final class Left<A, B> extends Either<A, B> {
    private final A value;

    Left(A value) {
        this.value = requireNonNull(value);
    }

    A value() {
        return value;
    }

    @Override
    <C> C cast(Function<Left<A, B>, C> left, Function<Right<A, B>, C> right) {
        return left.apply(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Left<?, ?> left = (Left<?, ?>) o;

        return value.equals(left.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
