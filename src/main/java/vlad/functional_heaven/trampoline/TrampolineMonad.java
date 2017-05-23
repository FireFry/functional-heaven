package vlad.functional_heaven.trampoline;

import vlad.functional_heaven.function.Function;
import vlad.functional_heaven.higher_order.Holed;
import vlad.functional_heaven.higher_order.Monad;

import static vlad.functional_heaven.trampoline.Trampoline.resolve;

enum TrampolineMonad implements Monad<Trampoline<?>> {
    INSTANCE;

    @Override
    public <A> Holed<Trampoline<?>, A> pure(A a) {
        return Trampoline.pure(a);
    }

    @Override
    public <A, B> Holed<Trampoline<?>, B> flatMap(Holed<Trampoline<?>, A> t, Function<A, Holed<Trampoline<?>, B>> f) {
        return resolve(t).flatMap(a -> resolve(f.apply(a)));
    }

    @Override
    public <A, B> Holed<Trampoline<?>, B> map(Holed<Trampoline<?>, A> t, Function<A, B> f) {
        return resolve(t).map(f);
    }

    @Override
    public <A, B> Holed<Trampoline<?>, B> apply(Holed<Trampoline<?>, A> t, Holed<Trampoline<?>, Function<A, B>> f) {
        return resolve(f).flatMap(g -> resolve(t).map(g));
    }
}
