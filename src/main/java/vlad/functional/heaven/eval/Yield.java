package vlad.functional.heaven.eval;

import vlad.functional.heaven.function.Function;

import static java.util.Objects.requireNonNull;

final class Yield<A> extends Eval<A> {
    private final A value;

    Yield(A value) {
        this.value = requireNonNull(value);
    }

    A value() {
        return value;
    }

    @Override
    <B> B cast(Function<Yield<A>, B> yield, Function<Suspend<A>, B> suspend) {
        return yield.apply(this);
    }
}
