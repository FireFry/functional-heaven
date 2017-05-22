package vlad.functional.heaven.eval;

import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

final class Suspend<A> extends Eval<A> {
    private final Supplier<Eval<A>> next;

    Suspend(Supplier<Eval<A>> next) {
        this.next = requireNonNull(next);
    }

    Supplier<Eval<A>> next() {
        return next;
    }

    @Override
    <B> B cast(Function<Yield<A>, B> yield, Function<Suspend<A>, B> suspend) {
        return suspend.apply(this);
    }
}