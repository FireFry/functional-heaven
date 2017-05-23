package vlad.functional_heaven.free;

import vlad.functional_heaven.function.Function;
import vlad.functional_heaven.higher_order.Holed;

import static java.util.Objects.requireNonNull;

final class Pure<T extends Holed<T, ?>, A> extends Free<T, A> {
    private final A value;

    Pure(A value) {
        this.value = requireNonNull(value);
    }

    public A value() {
        return value;
    }

    @Override
    <Z, B> B cast(Function<Pure<T, A>, B> pure, Function<Suspend<T, A>, B> suspend, Function<FlatMapped<T, Z, A>, B> flatMapped) {
        return pure.apply(this);
    }
}
