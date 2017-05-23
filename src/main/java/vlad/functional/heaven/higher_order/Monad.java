package vlad.functional.heaven.higher_order;

import vlad.functional.heaven.function.Function;

public interface Monad<T extends Holed<T, ?>> extends Applicative<T> {

  @Override
  <A> Holed<T, A> pure(A a);

  <A, B> Holed<T, B> flatMap(Holed<T, A> t, Function<A, Holed<T, B>> f);

  @Override
  default <A, B> Holed<T, B> map(Holed<T, A> t, Function<A, B> f){
    return flatMap(t, a -> pure(f.apply(a)));
  }

  @Override
  default <A, B> Holed<T, B> apply(Holed<T, A> t, Holed<T, Function<A, B>> f) {
    return flatMap(f, g -> map(t, g));
  }

}
