package vlad.functional_heaven.monoids;

import vlad.functional_heaven.lower_order.Monoid;

public final class StringMonoid implements Monoid<String> {
    private static final StringMonoid INSTANCE = new StringMonoid();

    private StringMonoid() {

    }

    public static StringMonoid stringMonoid() {
        return INSTANCE;
    }

    private static final String EMPTY_STRING = "";

    @Override
    public String empty() {
        return EMPTY_STRING;
    }

    @Override
    public String apply(String first, String second) {
        return first + second;
    }
}
