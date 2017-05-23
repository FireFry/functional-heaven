package vlad.functional.heaven.list;

import vlad.functional.heaven.lower_order.Monoid;

import static vlad.functional.heaven.list.List.nil;

final class ListMonoid<A> implements Monoid<List<A>> {
    private static final ListMonoid INSTANCE = new ListMonoid();

    private ListMonoid() {

    }

    @SuppressWarnings("unchecked")
    static <A> ListMonoid<A> instance() {
        return INSTANCE;
    }

    @Override
    public List<A> empty() {
        return nil();
    }

    @Override
    public List<A> apply(List<A> first, List<A> second) {
        return first.append(second);
    }
}
