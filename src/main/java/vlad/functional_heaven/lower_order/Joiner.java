package vlad.functional_heaven.lower_order;

public interface Joiner<A> extends Semigroup<A> {

    A first();

    A delimiter();

    A last();

    static <A> Joiner<A> construct(Semigroup<A> semigroup, A first, A delimiter, A last) {
        return new JoinerConstruct<>(semigroup, first, delimiter, last);
    }

}
