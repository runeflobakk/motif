package no.motif;

import no.motif.f.Fn;
import no.motif.f.Predicate;

/**
 * Functions operating on {@link String strings}.
 *
 * @see Predicate
 * @see Fn
 */
public final class Strings {

    public static final Predicate<String> blank = new Predicate<String>() {
        @Override
        public boolean $(String value) {
            return value == null || value.trim().isEmpty();
        }
    };

    public static final Fn<String, String> trimmed = new Fn<String, String>() {
        @Override
        public String $(String value) {
            return value.trim();
        }
    };

    public static final Fn<String, String> lowerCased = new Fn<String, String>() {
        @Override
        public String $(String value) {
            return value.toLowerCase();
        }
    };

    public static final Fn<String, String> upperCased = new Fn<String, String>() {
        @Override
        public String $(String value) {
            return value.toUpperCase();
        }
    };

    private Strings() {}; static { new Strings(); }

}
