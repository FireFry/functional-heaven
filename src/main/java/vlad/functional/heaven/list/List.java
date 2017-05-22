package vlad.functional.heaven.list;

import vlad.functional.heaven.higher_order.Holed;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class List<A> implements Holed<List<?>, A> {

    List() {

    }

    public static <A> Nil<A> nil() {
        return Nil.instance();
    }

    public static <A> Cons<A> cons(A head) {
        return cons(head, nil());
    }

    public static <A> Cons<A> cons(A head, List<A> tail) {
        return new Cons<>(head, tail);
    }

    public static <A> List<A> resolve(Holed<List<?>, A> holed) {
        return (List<A>) holed;
    }

    public abstract <B> B cast(Function<Nil<A>, B> nil, Function<Cons<A>, B> cons);

    public <B> B match(Supplier<B> nilCase, BiFunction<A, List<A>, B> consCase) {
        return cast(
                nil -> nilCase.get(),
                cons -> consCase.apply(cons.head(), cons.tail()));
    }

}
