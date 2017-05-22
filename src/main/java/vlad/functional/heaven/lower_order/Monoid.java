package vlad.functional.heaven.lower_order;

public interface Monoid<A> extends Semigroup<A> {

    A empty();

}
