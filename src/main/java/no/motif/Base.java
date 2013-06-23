package no.motif;

import java.util.Objects;

import no.motif.f.Fn;
import no.motif.f.Predicate;

/**
 * Basic functions.
 *
 * @see Predicate
 * @see Fn
 */
public final class Base {

    public static <T> Predicate<T> not(final Predicate<T> predicate) {
        return new Predicate<T>() { @Override public boolean $(T value) { return !predicate.$(value); }};
    }

    public static <T> Predicate<T> equalTo(final T value) {
        return new Predicate<T>() { @Override public boolean $(T input) { return Objects.equals(input, value); }};
    }

    public static Predicate<Object> isNull = new Predicate<Object>() { @Override public boolean $(Object value) { return value == null; }};

    public static Predicate<Object> notNull = not(isNull);

    public static final Fn<Object, String> toString = new Fn<Object, String>() { @Override public String $(Object value) { return String.valueOf(value); }};


    private Base() {} static { new Base(); }

}
