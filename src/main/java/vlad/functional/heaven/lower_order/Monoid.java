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
            public A apply(A first, A second) {
                return semigroup.apply(first, second);
            }
        };
    }

}
