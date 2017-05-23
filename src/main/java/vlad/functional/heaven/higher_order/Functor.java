package vlad.functional.heaven.higher_order;

import vlad.functional.heaven.function.Function;

public interface Functor<T extends Holed<T, ?>> {

  <A, B> Holed<T, B> map(Holed<T, A> t, Function<A, B> f);

}
