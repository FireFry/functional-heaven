package vlad.functional.heaven.monoids;

import vlad.functional.heaven.lower_order.Monoid;

public enum StringMonoid implements Monoid<String> {
    INSTANCE;

    @Override
    public String empty() {
        return "";
    }

    @Override
    public String apply(String first, String second) {
        return first + second;
    }
}
