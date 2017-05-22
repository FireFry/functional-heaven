package vlad.functional.heaven.list;

import java.util.function.Function;

public final class Nil<A> extends List<A> {
    private static final Nil INSTANCE = new Nil();

    Nil() {

    }

    @SuppressWarnings("unchecked")
    static <A> Nil<A> instance() {
        return INSTANCE;
    }

    @Override
    public <B> B cast(Function<Nil<A>, B> nil, Function<Cons<A>, B> cons) {
        return nil.apply(this);
    }
}
