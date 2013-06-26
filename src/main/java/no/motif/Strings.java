package no.motif;

import static no.motif.Base.not;
import static no.motif.Chars.digit;
import static no.motif.Iterate.on;
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
        @Override public boolean $(String s) { return s == null || s.trim().isEmpty(); }};


    public static final Predicate<String> numeric = new Predicate<String>() {
        @Override public boolean $(String s) { return s != null && !s.isEmpty() && !on(s).filter(not(digit)).iterator().hasNext(); }};


    public static final Fn<String, String> trimmed = new NopOnNullFn<String, String>() {
        @Override protected String $nullsafe(String s) { return s.trim(); }};


    public static final Fn<String, String> lowerCased = new NopOnNullFn<String, String>() {
        @Override protected String $nullsafe(String s) { return s.toLowerCase(); }};


    public static final Fn<String, String> upperCased = new NopOnNullFn<String, String>() {
        @Override protected String $nullsafe(String s) { return s.toUpperCase(); }};


    public static final Fn<String, Integer> length  = new Fn<String, Integer>() {
        @Override public Integer $(String s) { return s != null ? s.length() : 0; }};


    private Strings() {} static { new Strings(); }

}
