package vlad.functional_heaven.lower_order;

import vlad.functional_heaven.function.BiFunction;

public interface Semigroup<A> extends BiFunction<A, A, A> {

    @Override
    A apply(A first, A second);

}
