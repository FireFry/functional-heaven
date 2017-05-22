package vlad.functional.heaven.either;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

final class Right<A, B> extends Either<A, B> {
    private final B value;

    Right(B value) {
        this.value = requireNonNull(value);
    }

    B value() {
        return value;
    }

    @Override
    <C> C cast(Function<Left<A, B>, C> left, Function<Right<A, B>, C> right) {
        return right.apply(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Right<?, ?> left = (Right<?, ?>) o;

        return value.equals(left.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
