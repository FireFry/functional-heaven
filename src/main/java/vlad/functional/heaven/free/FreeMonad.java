package vlad.functional.heaven.free;

import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.higher_order.Monad;

import java.util.function.Function;

import static vlad.functional.heaven.free.Free.resolve;

final class FreeMonad<T extends Holed<T, ?>> implements Monad<Free<T, ?>> {
    private static final FreeMonad INSTANCE = new FreeMonad();

    @SuppressWarnings("unchecked")
    static <T extends Holed<T, ?>> FreeMonad<T> instance() {
        return INSTANCE;
    }

    @Override
    public <A> Holed<Free<T, ?>, A> pure(A a) {
        return Free.pure(a);
    }

    @Override
    public <A, B> Holed<Free<T, ?>, B> flatMap(Holed<Free<T, ?>, A> t, Function<A, Holed<Free<T, ?>, B>> f) {
        return resolve(t).flatMap(a -> resolve(f.apply(a)));
    }

    @Override
    public <A, B> Holed<Free<T, ?>, B> map(Holed<Free<T, ?>, A> t, Function<A, B> f) {
        return resolve(t).map(f);
    }

    @Override
    public <A, B> Holed<Free<T, ?>, B> apply(Holed<Free<T, ?>, A> t, Holed<Free<T, ?>, Function<A, B>> f) {
        return resolve(f).flatMap(g -> resolve(t).map(g));
    }
}
