package vlad.functional.heaven.function;

import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.higher_order.Monad;

import static vlad.functional.heaven.function.Supplier.resolve;

enum SupplierMonad implements Monad<Supplier<?>> {
    INSTANCE;

    @Override
    public <A> Supplier<A> pure(A a) {
        return () -> a;
    }

    @Override
    public <A, B> Holed<Supplier<?>, B> flatMap(Holed<Supplier<?>, A> t, Function<A, Holed<Supplier<?>, B>> f) {
        return resolve(t).flatMap(a -> resolve(f.apply(a)));
    }

    @Override
    public <A, B> Holed<Supplier<?>, B> map(Holed<Supplier<?>, A> t, Function<A, B> f) {
        return resolve(t).map(f);
    }

    @Override
    public <A, B> Holed<Supplier<?>, B> apply(Holed<Supplier<?>, A> t, Holed<Supplier<?>, Function<A, B>> f) {
        return resolve(f).flatMap(g -> resolve(t).map(g));
    }
}
