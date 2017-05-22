package vlad.functional.heaven.higher_order;

public interface Natural<T extends Holed<T, ?>, R extends Holed<R, ?>> {

  <A> Holed<R, A> apply(Holed<T, A> t);

}
