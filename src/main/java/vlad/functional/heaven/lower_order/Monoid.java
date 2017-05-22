package vlad.functional.heaven.lower_order;

public interface Monoid<A> extends Semigroup<A> {

    A empty();

    static <A> Monoid<A> monoid(A empty, Semigroup<A> semigroup) {
        return new Monoid<A>() {
            @Override
            public A empty() {
                return empty;
            }

            @Override
            public A apply(A a, A a2) {
                return semigroup.apply(a, a2);
            }
        };
    }

}
