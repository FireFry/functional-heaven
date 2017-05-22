package vlad.functional.heaven.list;

import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.higher_order.Monad;

import java.util.function.Function;

import static vlad.functional.heaven.list.List.*;

enum ListMonad implements Monad<List<?>> {
    INSTANCE;

    @Override
    public <A> Holed<List<?>, A> pure(A a) {
        return cons(a);
    }

    @Override
    public <A, B> Holed<List<?>, B> flatMap(Holed<List<?>, A> t, Function<A, Holed<List<?>, B>> f) {
        return resolve(t).flatMap(a -> resolve(f.apply(a)));
    }

    @Override
    public <A, B> Holed<List<?>, B> map(Holed<List<?>, A> t, Function<A, B> f) {
        return resolve(t).map(f);
    }

    @Override
    public <A, B> Holed<List<?>, B> apply(Holed<List<?>, A> t, Holed<List<?>, Function<A, B>> f) {
        return resolve(f).flatMap(g -> resolve(t).map(g));
    }
}
