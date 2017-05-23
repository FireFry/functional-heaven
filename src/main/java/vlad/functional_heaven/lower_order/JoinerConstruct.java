package vlad.functional_heaven.lower_order;

final class JoinerConstruct<A> implements Joiner<A> {
    private final Semigroup<A> semigroup;
    private final A first;
    private final A delimiter;
    private final A last;

    JoinerConstruct(Semigroup<A> semigroup, A first, A delimiter, A last) {
        this.semigroup = semigroup;
        this.first = first;
        this.delimiter = delimiter;
        this.last = last;
    }

    @Override
    public A first() {
        return first;
    }

    @Override
    public A delimiter() {
        return delimiter;
    }

    @Override
    public A last() {
        return last;
    }

    @Override
    public A apply(A first, A second) {
        return semigroup.apply(first, second);
    }
}
