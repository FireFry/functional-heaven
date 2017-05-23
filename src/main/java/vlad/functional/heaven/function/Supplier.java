package vlad.functional.heaven.function;

import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.higher_order.Monad;

public interface Supplier<A> extends Holed<Supplier<?>, A> {

    static <A> Supplier<A> resolve(Holed<Supplier<?>, A> holed) {
        return (Supplier<A>) holed;
    }

    static Monad<Supplier<?>> monad() {
        return SupplierMonad.INSTANCE;
    }

    A get();

    default <B> Supplier<B> flatMap(Function<A, Supplier<B>> f) {
        return f.apply(get());
    }

    default <B> Supplier<B> map(Function<A, B> f) {
        return () -> f.apply(get());
    }

}
