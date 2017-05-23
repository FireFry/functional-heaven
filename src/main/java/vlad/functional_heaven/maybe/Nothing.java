package vlad.functional_heaven.maybe;

import vlad.functional_heaven.function.Function;

public final class Nothing<A> extends Maybe<A> {
    private static final Nothing INSTANCE = new Nothing();

    private Nothing() {

    }

    @SuppressWarnings("unchecked")
    static <A> Nothing<A> instance() {
        return INSTANCE;
    }

    @Override
    public <B> B cast(Function<Nothing<A>, B> nothing, Function<Just<A>, B> just) {
        return nothing.apply(this);
    }
}
