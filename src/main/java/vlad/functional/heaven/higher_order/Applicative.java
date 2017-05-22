package vlad.functional.heaven.higher_order;

public interface Applicative<T extends Holed<T, ?>> extends Apply<T> {

  <A> Holed<T, A> pure(A x);

}
