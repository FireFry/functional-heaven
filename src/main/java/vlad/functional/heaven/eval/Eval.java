package vlad.functional.heaven.eval;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Eval<A> {

    Eval() {

    }

    public static <A> Eval<A> yield(A a) {
        return new Yield<>(a);
    }

    public static <A> Eval<A> suspend(Supplier<Eval<A>> next) {
        return new Suspend<>(next);
    }

    abstract <B> B cast(Function<Yield<A>, B> yield, Function<Suspend<A>, B> suspend);

    <B> B match(Function<A, B> yieldCase, Function<Supplier<Eval<A>>, B> suspendCase) {
        return cast(
                yield -> yieldCase.apply(yield.value()),
                suspend -> suspendCase.apply(suspend.next()));
    }

    public A run() {
        Eval<A> eval = this;
        while (!eval.hasInstantValue()) {
            eval = eval.step();
        }
        return eval.getValue();
    }

    private static <A> A run(Eval<A> eval) {
        while (!eval.hasInstantValue()) {
            eval = eval.step();
        }
        return eval.getValue();
    }

    private Eval<A> step() {
        return cast(
                yield -> yield,
                suspend -> suspend.next().get());
    }

    private boolean hasInstantValue() {
        return cast(
                yield -> true,
                suspend -> false);
    }

    private A getValue() {
        return match(
                value -> value,
                next -> next.get().getValue());
    }

}
