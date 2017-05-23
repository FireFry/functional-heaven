package vlad.functional_heaven.list;

import vlad.functional_heaven.function.Function;

import static java.util.Objects.requireNonNull;

public final class Cons<A> extends List<A> {
    private final A head;
    private final List<A> tail;

    Cons(A head, List<A> tail) {
        this.head = requireNonNull(head);
        this.tail = requireNonNull(tail);
    }

    public A head() {
        return head;
    }

    public List<A> tail() {
        return tail;
    }

    @Override
    public <B> B cast(Function<Nil<A>, B> nil, Function<Cons<A>, B> cons) {
        return cons.apply(this);
    }
}
