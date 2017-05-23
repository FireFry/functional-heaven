package vlad.functional.heaven.maybe;

import vlad.functional.heaven.function.Function;
import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.higher_order.Monad;

import static vlad.functional.heaven.maybe.Maybe.just;
import static vlad.functional.heaven.maybe.Maybe.resolve;

enum MaybeMonad implements Monad<Maybe<?>> {
    INSTANCE;

    @Override
    public <A> Holed<Maybe<?>, A> pure(A a) {
        return just(a);
    }

    @Override
    public <A, B> Holed<Maybe<?>, B> flatMap(Holed<Maybe<?>, A> t, Function<A, Holed<Maybe<?>, B>> f) {
        return resolve(t).flatMap(a -> resolve(f.apply(a)));
    }

    @Override
    public <A, B> Holed<Maybe<?>, B> map(Holed<Maybe<?>, A> t, Function<A, B> f) {
        return resolve(t).map(f);
    }

    @Override
    public <A, B> Holed<Maybe<?>, B> apply(Holed<Maybe<?>, A> t, Holed<Maybe<?>, Function<A, B>> f) {
        return resolve(f).flatMap(g -> resolve(t).map(g));
    }
}
