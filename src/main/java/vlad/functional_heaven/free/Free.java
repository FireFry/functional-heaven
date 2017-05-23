package vlad.functional_heaven.free;

import vlad.functional_heaven.either.Either;
import vlad.functional_heaven.eval.Eval;
import vlad.functional_heaven.function.Function;
import vlad.functional_heaven.higher_order.Functor;
import vlad.functional_heaven.higher_order.Holed;
import vlad.functional_heaven.higher_order.Monad;
import vlad.functional_heaven.higher_order.Natural;

import static vlad.functional_heaven.either.Either.left;
import static vlad.functional_heaven.either.Either.right;
import static vlad.functional_heaven.eval.Eval.*;
import static vlad.functional_heaven.eval.Eval.yield;

public abstract class Free<T extends Holed<T, ?>, A> implements Holed<Free<T, ?>, A> {

    Free() {

    }

    public static <T extends Holed<T, ?>, A> Free<T, A> pure(A a) {
        return new Pure<>(a);
    }

    public static <T extends Holed<T, ?>, A> Free<T, A> suspend(Holed<T, A> value) {
        return new Suspend<>(value);
    }

    public static <T extends Holed<T, ?>, Z, A> Free<T, A> flatMapped(Free<T, Z> prev, Function<Z, Free<T, A>> f) {
        return new FlatMapped<>(prev, f);
    }

    public static <T extends Holed<T, ?>> Monad<Free<T, ?>> monad() {
        return FreeMonad.instance();
    }

    public static <T extends Holed<T, ?>, A> Free<T, A> resolve(Holed<Free<T, ?>, A> holed) {
        return (Free<T, A>) holed;
    }

    abstract <Z, B> B cast(Function<Pure<T, A>, B> pure, Function<Suspend<T, A>, B> suspend, Function<FlatMapped<T, Z, A>, B> flatMapped);

    public <B> Free<T, B> flatMap(Function<A, Free<T, B>> f) {
        return cast(
                pure -> flatMapped(pure, f),
                suspend -> flatMapped(suspend, f),
                flatMapped -> flatMapped(flatMapped.prev(), s -> flatMapped(flatMapped.f().apply(s), f)));
    }

    public <B> Free<T, B> map(Function<A, B> f) {
        return flatMap(t -> pure(f.apply(t)));
    }

    public final <R extends Holed<R, ?>> Holed<R, A> foldMap(Functor<T> functor, Monad<R> monad, Natural<T, R> natural){
        return resume(functor).eval().match(
                left -> monad.flatMap(monad.flatMap(monad.pure(left), t -> natural.apply(t)), x -> x.foldMap(functor, monad, natural)),
                right -> monad.pure(right));
    }

    public A run(Functor<T> functor, Function<Holed<T, Free<T, A>>, Free<T, A>> f) {
        return runEval(functor, f).eval();
    }

    private Eval<A> runEval(Functor<T> functor, Function<Holed<T, Free<T, A>>, Free<T, A>> f) {
        return resume(functor).eval().match(
                next -> defer(() -> f.apply(next).runEval(functor, f)),
                result -> yield(result));
    }

    private Eval<Either<Holed<T, Free<T, A>>, A>> resume(Functor<T> functor) {
        return cast(
                pure -> yield(right(pure.value())),
                suspend -> yield(left(functor.map(suspend.value(), Free::pure))),
                flatMapped -> flatMapped.prev().cast(
                        pure2 -> defer(() -> flatMapped.f().apply(pure2.value()).resume(functor)),
                        suspend2 -> yield(left(functor.map(suspend2.value(), flatMapped.f()))),
                        flatMapped2 -> defer(() -> flatMapped2.flatMap(flatMapped.f()).resume(functor))));
    }

}
