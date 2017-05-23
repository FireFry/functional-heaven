package vlad.functional_heaven.list;

import vlad.functional_heaven.eval.Eval;
import vlad.functional_heaven.function.BiFunction;
import vlad.functional_heaven.function.Function;
import vlad.functional_heaven.function.Supplier;
import vlad.functional_heaven.higher_order.Holed;
import vlad.functional_heaven.higher_order.Monad;
import vlad.functional_heaven.lower_order.Joiner;
import vlad.functional_heaven.lower_order.Monoid;

import static vlad.functional_heaven.eval.Eval.defer;
import static vlad.functional_heaven.eval.Eval.yield;
import static vlad.functional_heaven.lower_order.Joiner.joiner;
import static vlad.functional_heaven.monoids.StringMonoid.stringMonoid;

public abstract class List<A> implements Holed<List<?>, A> {

    List() {

    }

    public static <A> List<A> nil() {
        return Nil.instance();
    }

    public static <A> List<A> cons(A head) {
        return cons(head, nil());
    }

    public static <A> List<A> cons(A head, List<A> tail) {
        return new Cons<>(head, tail);
    }

    public static <A> List<A> of(A... elements) {
        return copyOf(elements);
    }

    public static <A> List<A> copyOf(A[] elements) {
        return copyOfEval(elements, elements.length - 1, nil()).eval();
    }

    private static <A> Eval<List<A>> copyOfEval(A[] elements, int index, List<A> acc) {
        return index < 0 ?
                yield(acc) :
                defer(() -> copyOfEval(elements, index - 1, cons(elements[index], acc)));
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

    public <B> B foldl(B acc, BiFunction<B, A, B> f) {
        return foldlEval(f, acc).eval();
    }

    private <B> Eval<B> foldlEval(BiFunction<B, A, B> f, B acc) {
        return match(
                () -> yield(acc),
                (x, xs) -> defer(() -> xs.foldlEval(f, f.apply(acc, x))));
    }

    public <B> B foldr(BiFunction<A, B, B> f, B acc) {
        return revert().foldl(acc, (b, a) -> f.apply(a, b));
    }

    public A fold(Monoid<A> monoid) {
        return foldl(monoid.empty(), monoid);
    }

    public <B> List<B> map(Function<A, B> f) {
        return foldr((a, list) -> cons(f.apply(a), list), nil());
    }

    public <B> List<B> flatMap(Function<A, List<B>> f) {
        return map(f).fold(monoid());
    }

    public List<A> revert() {
        return foldl(nil(), (list, a) -> cons(a, list));
    }

    public List<A> append(List<A> other) {
        return revert().foldl(other, (list, a) -> cons(a, list));
    }

    public A join(Joiner<A> joiner) {
        return joinEval(joiner, joiner.first()).eval();
    }

    private Eval<A> joinEval(Joiner<A> joiner, A acc) {
        return match(
                () -> yield(joiner.apply(acc, joiner.last())),
                (x, xs) -> defer(() -> xs.joinEval(joiner, joiner.apply(acc, xs.cast(
                        nil -> x,
                        cons -> joiner.apply(x, joiner.delimiter()))))));
    }

    @Override
    public String toString() {
        return map(String::valueOf).join(joiner(stringMonoid(), "[", ", ", "]"));
    }

}
