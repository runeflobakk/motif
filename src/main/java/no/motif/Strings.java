package no.motif;

import no.motif.f.Fn;
import no.motif.f.NopOnNullFn;
import no.motif.f.Predicate;

/**
 * Functions operating on {@link String strings}.
 *
 * @see Predicate
 * @see Fn
 */
public final class Strings {

    public static final Predicate<String> blank = new Predicate<String>() {
        @Override public boolean $(String value) { return value == null || value.trim().isEmpty(); }};


    public static final Fn<String, String> trimmed = new NopOnNullFn<String, String>() {
        @Override protected String $nullsafe(String value) { return value.trim(); }};


    public static final Fn<String, String> lowerCased = new NopOnNullFn<String, String>() {
        @Override protected String $nullsafe(String value) { return value.toLowerCase(); }};


    public static final Fn<String, String> upperCased = new NopOnNullFn<String, String>() {
        @Override protected String $nullsafe(String value) { return value.toUpperCase(); }};


    private Strings() {}; static { new Strings(); }

}
