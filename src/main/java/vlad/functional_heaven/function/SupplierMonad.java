package vlad.functional_heaven.function;

import vlad.functional_heaven.higher_order.Holed;
import vlad.functional_heaven.higher_order.Monad;

enum SupplierMonad implements Monad<Supplier<?>> {
    INSTANCE;

    @Override
    public <A> Supplier<A> pure(A a) {
        return () -> a;
    }

    @Override
    public <A, B> Holed<Supplier<?>, B> flatMap(Holed<Supplier<?>, A> t, Function<A, Holed<Supplier<?>, B>> f) {
        return Supplier.resolve(t).flatMap(a -> Supplier.resolve(f.apply(a)));
    }

    @Override
    public <A, B> Holed<Supplier<?>, B> map(Holed<Supplier<?>, A> t, Function<A, B> f) {
        return Supplier.resolve(t).map(f);
    }

    @Override
    public <A, B> Holed<Supplier<?>, B> apply(Holed<Supplier<?>, A> t, Holed<Supplier<?>, Function<A, B>> f) {
        return Supplier.resolve(f).flatMap(g -> Supplier.resolve(t).map(g));
    }
}
