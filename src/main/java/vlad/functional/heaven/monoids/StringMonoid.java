package vlad.functional.heaven.monoids;

import vlad.functional.heaven.lower_order.Monoid;

public enum StringMonoid implements Monoid<String> {
    INSTANCE;

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
