package vlad.functional_heaven.function;

import vlad.functional_heaven.higher_order.Holed;

public interface BiFunction<A, B, C> extends Holed<BiFunction<A, B, ?>, C> {

    C apply(A first, B second);

    static <A, B, C> BiFunction<A, B, C> resolve(Holed<BiFunction<A, B, ?>, C> holed) {
        return (BiFunction<A, B, C>) holed;
    }

}
