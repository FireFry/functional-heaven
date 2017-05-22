package vlad.functional.heaven.higher_order;

import java.util.function.Function;

public interface Apply<T extends Holed<T, ?>> extends Functor<T> {

  <A, B> Holed<T, B> apply(Holed<T, A> t, Holed<T, Function<A, B>> f);

}
