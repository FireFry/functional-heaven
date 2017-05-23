package vlad.functional_heaven.eval;

import vlad.functional_heaven.function.Function;
import vlad.functional_heaven.function.Supplier;

public abstract class Eval<A> {

    Eval() {

    }

    public static <A> Eval<A> yield(A a) {
        return new Yield<>(a);
    }

    public static <A> Eval<A> defer(Supplier<Eval<A>> next) {
        return new Defer<>(next);
    }

    abstract <B> B cast(Function<Yield<A>, B> yield, Function<Defer<A>, B> defer);

    <B> B match(Function<A, B> yieldCase, Function<Supplier<Eval<A>>, B> deferCase) {
        return cast(
                yield -> yieldCase.apply(yield.value()),
                defer -> deferCase.apply(defer.next()));
    }

    public A eval() {
        Eval<A> eval = this;
        while (!eval.hasInstantValue()) {
            eval = eval.step();
        }
        return eval.getValue();
    }

    private static <A> A eval(Eval<A> eval) {
        while (!eval.hasInstantValue()) {
            eval = eval.step();
        }
        return eval.getValue();
    }

    private Eval<A> step() {
        return cast(
                yield -> yield,
                defer -> defer.next().get());
    }

    private boolean hasInstantValue() {
        return cast(
                yield -> true,
                defer -> false);
    }

    private A getValue() {
        return match(
                value -> value,
                next -> next.get().getValue());
    }

}
