package vlad.functional_heaven.eval;

import vlad.functional_heaven.function.Function;
import vlad.functional_heaven.function.Supplier;

import static java.util.Objects.requireNonNull;

final class Defer<A> extends Eval<A> {
    private final Supplier<Eval<A>> next;

    Defer(Supplier<Eval<A>> next) {
        this.next = requireNonNull(next);
    }

    Supplier<Eval<A>> next() {
        return next;
    }

    @Override
    <B> B cast(Function<Yield<A>, B> yield, Function<Defer<A>, B> defer) {
        return defer.apply(this);
    }
}
