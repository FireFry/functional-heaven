package vlad.functional.heaven.maybe;

import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.higher_order.Monad;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Maybe<A> implements Holed<Maybe<?>, A> {

    Maybe() {

    }

    public static <A> Just<A> just(A value) {
        return new Just<>(value);
    }

    public static <A> Nothing<A> nothing() {
        return Nothing.instance();
    }

    public static Monad<Maybe<?>> monad() {
        return MaybeMonad.INSTANCE;
    }

    public static <A> Maybe<A> resolve(Holed<Maybe<?>, A> holed) {
        return (Maybe<A>) holed;
    }

    public abstract <B> B cast(Function<Nothing<A>, B> nothing, Function<Just<A>, B> just);

    public final <B> B match(Supplier<B> nothingCase, Function<A, B> justCase) {
        return cast(
                nothing -> nothingCase.get(),
                just -> justCase.apply(just.value()));
    }

    public final <B> Maybe<B> map(Function<A, B> f) {
        return match(
                () -> nothing(),
                a -> just(f.apply(a)));
    }

    public final <B> Maybe<B> flatMap(Function<A, Maybe<B>> f) {
        return match(
                () -> nothing(),
                a -> f.apply(a));
    }

    @Override
    public final String toString() {
        return cast(
                nothing -> "Nothing",
                just -> "Just " + just.value());
    }

}
