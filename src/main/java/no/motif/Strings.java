package no.motif;

import static no.motif.Base.not;
import static no.motif.Chars.digit;
import static no.motif.Iterate.on;

import no.motif.f.Fn;
import no.motif.f.Fn2;
import no.motif.f.PassThruIfNullOrElse;
import no.motif.f.FalseIfNullOrElse;
import no.motif.f.Predicate;
import no.motif.f.Predicate.Always;

/**
 * Functions operating on {@link String strings}.
 *
 * @see Predicate
 * @see Fn
 */
public final class Strings {


    public static final Predicate<String> blank = new Predicate<String>() {
        @Override public boolean $(String s) { return s == null || s.trim().isEmpty(); }};


    public static final Predicate<String> numeric = new FalseIfNullOrElse<String>() {
        @Override protected boolean $nullsafe(String s) {
        return !s.isEmpty() && !on(s).filter(not(digit)).iterator().hasNext(); }};


    public static final Fn<String, String> trimmed = new PassThruIfNullOrElse<String, String>() {
        @Override protected String $nullsafe(String s) { return s.trim(); }};


    public static final Fn<String, String> lowerCased = new PassThruIfNullOrElse<String, String>() {
        @Override protected String $nullsafe(String s) { return s.toLowerCase(); }};


    public static final Fn<String, String> upperCased = new PassThruIfNullOrElse<String, String>() {
        @Override protected String $nullsafe(String s) { return s.toUpperCase(); }};


    public static final Fn<String, Integer> length  = new Fn<String, Integer>() {
        @Override public Integer $(String s) { return s != null ? s.length() : 0; }};


    /**
     * @see Integer#valueOf(String)
     */
    public static final Fn<String, Integer> toInt = new Fn<String, Integer>() {
        @Override public Integer $(String numeric) { return numeric != null ? Integer.valueOf(numeric) : 0; }};


    /**
     * @see Double#valueOf(String)
     */
    public static final Fn<String, Double> toDouble = new Fn<String, Double>() {
        @Override public Double $(String decimalValue) { return decimalValue != null ? Double.valueOf(decimalValue) : 0; }};


    /**
     * @see String#toCharArray()
     */
    public static final Fn<CharSequence, Iterable<Character>> toChars = new Fn<CharSequence, Iterable<Character>>() {
        @Override
        public Iterable<Character> $(CharSequence value) {
            return Iterate.on(value);
        }
    };


    public static final Fn2<String, Object, String> concat = new Fn2<String, Object, String>() {
        @Override public String $(String acc, Object c) { return acc + c; }};


    /**
     * @see String#contentEquals(CharSequence)
     */
    public static Predicate<String> contains(final CharSequence charSequence) {
        return charSequence == null ? Always.<String>no() : new FalseIfNullOrElse<String>() {
        @Override protected boolean $nullsafe(String string) { return string.contains(charSequence); }}; }


    /**
     * @see String#startsWith(String)
     */
    public static Predicate<String> startsWith(final String prefix) {
        return prefix == null ? Always.<String>no() : new FalseIfNullOrElse<String>() {
        @Override protected boolean $nullsafe(String string) { return string.startsWith(prefix); }}; }


    /**
     * @see String#endsWith(String)
     */
    public static Predicate<String> endsWith(final String suffix) {
        return suffix == null ? Always.<String>no() : new FalseIfNullOrElse<String>() {
        @Override protected boolean $nullsafe(String string) { return string.endsWith(suffix); }}; }



    /**
     * @see String#matches(String)
     */
    public static Predicate<String> matches(final String regex) {
        return regex == null ? Always.<String>no() : new FalseIfNullOrElse<String>() {
        @Override protected boolean $nullsafe(String string) { return string.matches(regex); }};}

    private Strings() {}

}
