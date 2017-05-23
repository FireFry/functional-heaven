package vlad.functional.heaven.either;

import vlad.functional.heaven.function.Function;
import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.higher_order.Monad;

public abstract class Either<A, B> implements Holed<Either<A, ?>, B> {

    Either() {

    }

    public static <A, B> Either<A, B> left(A a) {
        return new Left<>(a);
    }

    public static <A, B> Either<A, B> right(B b) {
        return new Right<>(b);
    }

    public static <A> Monad<Either<A, ?>> monad() {
        return EitherMonad.instance();
    }

    public static <A, B> Either<A, B> resolve(Holed<Either<A, ?>, B> holed) {
        return (Either<A, B>) holed;
    }

    abstract <C> C cast(Function<Left<A, B>, C> left, Function<Right<A, B>, C> right);

    public <C> C match(Function<A, C> leftCase, Function<B, C> rightCase) {
        return cast(
                left -> leftCase.apply(left.value()),
                right -> rightCase.apply(right.value()));
    }

    public <C> Either<A, C> map(Function<B, C> f) {
        return match(
                left -> left(left),
                right -> right(f.apply(right)));
    }

    public <C> Either<A, C> flatMap(Function<B, Either<A, C>> f) {
        return match(
                left -> left(left),
                right -> f.apply(right));
    }

    @Override
    public final String toString() {
        return match(
                left -> "Left " + left,
                right -> "Right " + right);
    }

}
