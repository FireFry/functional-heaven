package vlad.functional.heaven.either;

import vlad.functional.heaven.function.Function;
import vlad.functional.heaven.higher_order.Holed;
import vlad.functional.heaven.higher_order.Monad;

import static vlad.functional.heaven.either.Either.resolve;
import static vlad.functional.heaven.either.Either.right;

final class EitherMonad<A> implements Monad<Either<A, ?>> {
    private static final EitherMonad INSTANCE = new EitherMonad();

    EitherMonad() {

    }

    @SuppressWarnings("unchecked")
    public static <A> EitherMonad<A> instance() {
        return INSTANCE;
    }

    @Override
    public <B> Holed<Either<A, ?>, B> pure(B b) {
        return right(b);
    }

    @Override
    public <B, C> Holed<Either<A, ?>, C> flatMap(Holed<Either<A, ?>, B> t, Function<B, Holed<Either<A, ?>, C>> f) {
        return resolve(t).flatMap(b -> resolve(f.apply(b)));
    }

    @Override
    public <B, C> Holed<Either<A, ?>, C> map(Holed<Either<A, ?>, B> t, Function<B, C> f) {
        return resolve(t).map(f);
    }

    @Override
    public <B, C> Holed<Either<A, ?>, C> apply(Holed<Either<A, ?>, B> t, Holed<Either<A, ?>, Function<B, C>> f) {
        return resolve(f).flatMap(g -> resolve(t).map(g));
    }
}
