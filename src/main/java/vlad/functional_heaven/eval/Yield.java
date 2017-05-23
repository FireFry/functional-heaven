package vlad.functional_heaven.eval;

import vlad.functional_heaven.function.Function;

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
    <B> B cast(Function<Yield<A>, B> yield, Function<Defer<A>, B> defer) {
        return yield.apply(this);
    }
}
