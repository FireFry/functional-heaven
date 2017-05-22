package vlad.functional.heaven.maybe;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Maybe<A> {

    Maybe() {

    }

    public static <A> Just<A> just(A value) {
        return new Just<>(value);
    }

    public static <A> Nothing<A> nothing() {
        return Nothing.instance();
    }

    public abstract <B> B cast(Function<Nothing<A>, B> nothing, Function<Just<A>, B> just);

    public final <B> B match(Supplier<B> nothingMatcher, Function<A, B> justMatcher) {
        return cast(
                nothing -> nothingMatcher.get(),
                just -> justMatcher.apply(just.value()));
    }

    @Override
    public final String toString() {
        return cast(
                nothing -> "Nothing",
                just -> "Just " + just.value());
    }

}
