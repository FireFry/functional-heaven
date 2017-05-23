package vlad.functional_heaven.list;

import vlad.functional_heaven.eval.Eval;
import vlad.functional_heaven.function.BiFunction;
import vlad.functional_heaven.function.Function;
import vlad.functional_heaven.function.Supplier;
import vlad.functional_heaven.higher_order.Holed;
import vlad.functional_heaven.higher_order.Monad;
import vlad.functional_heaven.lower_order.Monoid;
import vlad.functional_heaven.lower_order.Semigroup;
import vlad.functional_heaven.monoids.StringMonoid;

import static vlad.functional_heaven.eval.Eval.defer;
import static vlad.functional_heaven.eval.Eval.yield;

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

    public static <A> List<A> of(A... elements) {
        return copyOf(elements);
    }

    public static <A> List<A> copyOf(A[] elements) {
        return ofEval(elements, elements.length - 1, nil()).eval();
    }

    private static <A> Eval<List<A>> ofEval(A[] elements, int index, List<A> acc) {
        return index < 0 ?
                yield(acc) :
                defer(() -> ofEval(elements, index - 1, cons(elements[index], acc)));
    }

    public static List<Character> copyOf(char[] elements) {
        return ofEval(elements, elements.length - 1, nil()).eval();
    }

    private static Eval<List<Character>> ofEval(char[] elements, int index, List<Character> acc) {
        return index < 0 ?
                yield(acc) :
                defer(() -> ofEval(elements, index - 1, cons(elements[index], acc)));
    }

    public static List<Character> ofChars(String s) {
        return copyOf(s.toCharArray());
    }

    public static String toString(List<Character> chars) {
        return chars.foldMap(StringMonoid.INSTANCE, String::valueOf);
    }

    public static <A> Monoid<List<A>> monoid() {
        return ListMonoid.instance();
    }

    public static Monad<List<?>> monad() {
        return ListMonad.INSTANCE;
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

    public A fold(Monoid<A> monoid) {
        return foldEval(monoid, monoid.empty()).eval();
    }

    private Eval<A> foldEval(Semigroup<A> semigroup, A acc) {
        return match(
                () -> yield(acc),
                (x, xs) -> defer(() -> xs.foldEval(semigroup, semigroup.apply(acc, x))));
    }

    public <B> B foldMap(Monoid<B> monoid, Function<A, B> f) {
        return foldMapEval(monoid, f, monoid.empty()).eval();
    }

    private <B> Eval<B> foldMapEval(Semigroup<B> semigroup, Function<A, B> f, B acc) {
        return match(
                () -> yield(acc),
                (x, xs) -> defer(() -> xs.foldMapEval(semigroup, f, semigroup.apply(acc, f.apply(x)))));
    }

    public <B> List<B> map(Function<A, B> f) {
        return mapEval(f, nil()).eval();
    }

    private <B> Eval<List<B>> mapEval(Function<A, B> f, List<B> stack) {
        return match(
                () -> yield(stack.reverse()),
                (x, xs) -> defer(() -> xs.mapEval(f, cons(f.apply(x), stack))));
    }

    public <B> List<B> flatMap(Function<A, List<B>> f) {
        return foldMap(monoid(), f);
    }

    public List<A> reverse() {
        return reverseEval(nil()).eval();
    }

    private Eval<List<A>> reverseEval(List<A> acc) {
        return match(
                () -> yield(acc),
                (x, xs) -> defer(() -> xs.reverseEval(cons(x, acc))));
    }

    public List<A> append(List<A> other) {
        return reverse().appendReverseEval(other).eval();
    }

    private Eval<List<A>> appendReverseEval(List<A> other) {
        return match(
                () -> yield(other),
                (x, xs) -> defer(() -> xs.reverseEval(cons(x, other))));
    }

    @Override
    public String toString() {
        return "[" + match(() -> "", (x, xs) -> xs.foldMap(Monoid.monoid(x.toString(), (a, b) -> a + ", " + b), y -> y.toString())) + "]";
    }

}
