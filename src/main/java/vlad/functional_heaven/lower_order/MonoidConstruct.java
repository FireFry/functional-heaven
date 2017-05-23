package vlad.functional_heaven.lower_order;

final class MonoidConstruct<A> implements Monoid<A> {
    private final A empty;
    private final Semigroup<A> semigroup;

    MonoidConstruct(A empty, Semigroup<A> semigroup) {
        this.empty = empty;
        this.semigroup = semigroup;
    }

    @Override
    public A empty() {
        return empty;
    }

    @Override
    public A apply(A first, A second) {
        return semigroup.apply(first, second);
    }
}
