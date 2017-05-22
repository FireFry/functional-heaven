package vlad.functional.heaven.list;

import vlad.functional.heaven.eval.Eval;
import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.lower_order.Monoid;
import vlad.functional.heaven.lower_order.Semigroup;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static vlad.functional.heaven.eval.Eval.suspend;
import static vlad.functional.heaven.eval.Eval.yield;

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

    public <B> B fold(A empty, Semigroup<B> semigroup, Function<A, B> f) {
        return foldEval(f.apply(empty), semigroup, f).eval();
    }

    public <B> B fold(Monoid<B> monoid, Function<A, B> f) {
        return foldEval(monoid.empty(), monoid, f).eval();
    }

    private <B> Eval<B> foldEval(B acc, Semigroup<B> semigroup, Function<A, B> f) {
        return match(
                () -> yield(acc),
                (x, xs) -> suspend(() -> xs.foldEval(semigroup.apply(acc, f.apply(x)), semigroup, f)));
    }

    @Override
    public String toString() {
        return "[" + match(() -> "", (x, xs) -> fold(x, (a, b) -> a + ", " + b, String::valueOf)) + "]";
    }

}
