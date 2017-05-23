package vlad.functional_heaven.lower_order;

public interface Monoid<A> extends Semigroup<A> {

    A empty();

    static <A> Monoid<A> construct(A empty, Semigroup<A> semigroup) {
        return new MonoidConstruct<>(empty, semigroup);
    }

}
