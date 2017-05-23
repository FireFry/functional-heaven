package vlad.functional_heaven.higher_order;

public interface Applicative<T extends Holed<T, ?>> extends Apply<T> {

  <A> Holed<T, A> pure(A a);

}
