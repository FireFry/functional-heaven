package vlad.functional_heaven.free;

import vlad.functional_heaven.function.Function;
import vlad.functional_heaven.higher_order.Holed;

import static java.util.Objects.requireNonNull;

final class FlatMapped<T extends Holed<T, ?>, Z, A> extends Free<T, A> {
    private final Free<T, Z> prev;
    private final Function<Z, Free<T, A>> f;

    FlatMapped(Free<T, Z> prev, Function<Z, Free<T, A>> f) {
        this.prev = requireNonNull(prev);
        this.f = requireNonNull(f);
    }

    Free<T, Z> prev() {
        return prev;
    }

    Function<Z, Free<T, A>> f() {
        return f;
    }

    @Override
    <Z, B> B cast(Function<Pure<T, A>, B> pure, Function<Suspend<T, A>, B> suspend, Function<FlatMapped<T, Z, A>, B> flatMapped) {
        //noinspection unchecked
        return flatMapped.apply((FlatMapped<T, Z, A>) this);
    }
}
