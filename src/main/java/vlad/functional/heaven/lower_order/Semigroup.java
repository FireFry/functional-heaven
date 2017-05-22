package vlad.functional.heaven.lower_order;

import java.util.function.BiFunction;

public interface Semigroup<A> extends BiFunction<A, A, A> {

    @Override
    A apply(A first, A second);

}
