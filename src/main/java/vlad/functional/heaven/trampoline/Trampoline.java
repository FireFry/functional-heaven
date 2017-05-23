package vlad.functional.heaven.trampoline;

import vlad.functional.heaven.free.Free;
import vlad.functional.heaven.function.Function;
import vlad.functional.heaven.function.Supplier;
import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.higher_order.Monad;

public final class Trampoline<A> implements Holed<Trampoline<?>, A> {
    private final Free<Supplier<?>, A> free;

    private Trampoline(Free<Supplier<?>, A> free) {
        this.free = free;
    }
    
    public static <A> Trampoline<A> trampoline(Free<Supplier<?>, A> free) {
        return new Trampoline<>(free);
    }

    public static <A> Trampoline<A> pure(A a) {
        return trampoline(Free.pure(a));
    }

    public static <A> Trampoline<A> suspend(Supplier<A> value) {
        return trampoline(Free.suspend(value));
    }

    public static <A> Trampoline<A> resolve(Holed<Trampoline<?>, A> holed) {
        return (Trampoline<A>) holed;
    }

    public static Monad<Trampoline<?>> monad() {
        return TrampolineMonad.INSTANCE;
    }

    public <B> Trampoline<B> flatMap(Function<A, Trampoline<B>> f) {
        return trampoline(free.map(f).flatMap(a -> a.free));
    }

    public <B> Trampoline<B> map(Function<A, B> f) {
        return trampoline(free.flatMap(a -> Free.pure(f.apply(a))));
    }

    public A run() {
        return free.run(Supplier.monad(), a -> Supplier.resolve(a).get());
    }
}
