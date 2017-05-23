package vlad.functional_heaven.function;

import vlad.functional_heaven.higher_order.Holed;

public interface Function<A, B> extends Holed<Function<A, ?>, B> {

    B apply(A arg);

    static <A, B> Function<A, B> resolve(Holed<Function<A, ?>, B> holed) {
        return (Function<A, B>) holed;
    }

}
