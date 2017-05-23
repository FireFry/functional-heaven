package vlad.functional_heaven.either;

import vlad.functional_heaven.function.Function;
import vlad.functional_heaven.higher_order.Holed;
import vlad.functional_heaven.higher_order.Monad;

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
        return Either.right(b);
    }

    @Override
    public <B, C> Holed<Either<A, ?>, C> flatMap(Holed<Either<A, ?>, B> t, Function<B, Holed<Either<A, ?>, C>> f) {
        return Either.resolve(t).flatMap(b -> Either.resolve(f.apply(b)));
    }

    @Override
    public <B, C> Holed<Either<A, ?>, C> map(Holed<Either<A, ?>, B> t, Function<B, C> f) {
        return Either.resolve(t).map(f);
    }

    @Override
    public <B, C> Holed<Either<A, ?>, C> apply(Holed<Either<A, ?>, B> t, Holed<Either<A, ?>, Function<B, C>> f) {
        return Either.resolve(f).flatMap(g -> Either.resolve(t).map(g));
    }
}
