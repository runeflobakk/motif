package no.motif;

import static java.util.Arrays.asList;
import static no.motif.Base.all;
import static no.motif.Base.both;
import static no.motif.Base.equalTo;
import static no.motif.Base.exists;
import static no.motif.Base.not;
import static no.motif.Base.where;
import static no.motif.Chars.digit;
import static no.motif.Chars.letter;
import static no.motif.Chars.letterOrDigit;
import static no.motif.Chars.whitespace;
import static no.motif.Iterate.on;
import static no.motif.f.Apply.argsReversed;
import no.motif.f.Apply;
import no.motif.f.Fn;
import no.motif.f.Fn2;
import no.motif.f.Predicate;
import no.motif.f.Predicate.Always;
import no.motif.f.base.FalseIfNullOrElse;
import no.motif.f.base.PassThruIfNullOrElse;

/**
 * Functions operating on {@link String strings}.
 */
public final class Strings {

    /**
     * Converts a string to a <code>int</code> value using {@link Integer#valueOf(String)}.
     * If the string is <code>null</code>, 0 is yielded.
     */
    public static final Fn<String, Integer> toInt = new Fn<String, Integer>() {
        @Override public Integer $(String numeric) { return numeric != null ? Integer.valueOf(numeric) : 0; }};


    /**
     * Converts a string to a <code>double</code> value using {@link Double#valueOf(String)}.
     * If the string is <code>null</code>, 0 is yielded.
     */
    public static final Fn<String, Double> toDouble = new Fn<String, Double>() {
        @Override public Double $(String decimalValue) { return decimalValue != null ? Double.valueOf(decimalValue) : 0; }};


    /**
     * Splits a string into characters.
     */
    public static final Fn<String, Iterable<Character>> toChars = new Fn<String, Iterable<Character>>() {
        @Override public Iterable<Character> $(String value) { return Iterate.on(value); }};


    /**
     * Yields the bytes of a String.
     * @see String#getBytes()
     */
    public static final Fn<String, byte[]> bytes = new Fn<String, byte[]>() {
        @Override public byte[] $(String s) { return s != null ? s.getBytes() : new byte[0]; }};




    /**
     * A blank string is either <code>null</code>, empty, or
     * all characters {@link Chars#whitespace are whitespace}.
     */
    public static final Predicate<String> blank = where(toChars, all(whitespace));


    /**
     * A nonblank string has at least one character, and must contain at least
     * one character which is {@link Chars#whitespace not whitespace}.
     */
    public static final Predicate<String> nonblank = where(toChars, exists(not(whitespace)));


    /**
     * A numeric string must have at least one character, and all of them
     * must be {@link Chars#digit digits}.
     */
    public static final Predicate<String> numeric = nonblankAllChars(digit);


    /**
     * Alphanumeric strings are at least one character, and all
     * {@link Chars#digit digits} and/or {@link Chars#letter letters}.
     */
    public static final Predicate<String> alphanumeric = nonblankAllChars(letterOrDigit);


    /**
     * Alphabetic strings are at least one character, and all {@link Chars#letter letters}.
     */
    public static final Predicate<String> alphabetic = nonblankAllChars(letter);


    /**
     * Predicate verifying that all characters in a string satifies a given predicate.
     *
     * @param valid The predicate evaluating all characters in a string.
     */
    public static final Predicate<String> allChars(Predicate<Character> valid) {
        return where(toChars, all(valid)); }

    /**
     * Predicate verifying that strings are {@link #nonblank not blank} and
     * each char satisfies a given predicate.
     *
     * @param valid
     */
    public static final Predicate<String> nonblankAllChars(Predicate<Character> valid) {
        return both(nonblank).and(allChars(valid)); }


    /**
     * Trims a string, removing all leading and trailing whitespace.
     */
    public static final Fn<String, String> trimmed = new PassThruIfNullOrElse<String, String>() {
        @Override protected String $nullsafe(String s) { return s.trim(); }};


    /**
     * Convert a string to {@link String#toLowerCase() lower case}.
     */
    public static final Fn<String, String> lowerCased = new PassThruIfNullOrElse<String, String>() {
        @Override protected String $nullsafe(String s) { return s.toLowerCase(); }};


    /**
     * Convert a string to {@link String#toUpperCase() upper case}
     */
    public static final Fn<String, String> upperCased = new PassThruIfNullOrElse<String, String>() {
        @Override protected String $nullsafe(String s) { return s.toUpperCase(); }};


    /**
     * Gives the length of a string, i.e. the amount of characters. <code>null</code>
     * yields length 0.
     */
    public static final Fn<String, Integer> length = new Fn<String, Integer>() {
        @Override public Integer $(String s) { return s != null ? s.length() : 0; }};


    /**
     * Evaluate if strings are of a exact length.
     * <code>null<code>s are considered to have length zero.
     */
    public static final Predicate<String> hasLength(int exactLength) { return hasLength(equalTo(exactLength)); }


    /**
     * Evaluate if strings have accepted lengths.
     * <code>null<code>s are considered to have length zero.
     *
     * @param accepted The predicate evaluating accepted length.
     * @return The predicate evaluating string length.
     */
    public static final Predicate<String> hasLength(Predicate<? super Integer> accepted) { return where(length, accepted); }


    /**
     * Concatenate a string with the {@link Object#toString() string representation}
     * of an arbitrary object, i.e. <em>reduces</em> two strings to one.
     */
    public static final Fn2<Object, Object, String> concat = new Fn2<Object, Object, String>() {
        String emptyIfNull(Object o) { return (o != null? String.valueOf(o) : ""); }
        @Override public String $(Object acc, Object c) { return emptyIfNull(acc) + emptyIfNull(c); }};



    /**
     * Yields the given string with its characters in reversed order.
     */
    public static final Fn<String, String> reversed = new PassThruIfNullOrElse<String, String>() {
        @Override protected String $nullsafe(String s) { return new StringBuilder(s).reverse().toString(); }};




    /**
     * Determines if a substring is present in a string. A string never contains <code>null</code>,
     * nor does <code>null</code> contain any substring.
     *
     * @param charSequence The substring to find.
     * @return The predicate.
     */
    public static Predicate<String> contains(final CharSequence charSequence) {
        return charSequence == null ? Always.<String>no() : new FalseIfNullOrElse<String>() {
        @Override protected boolean $nullsafe(String string) { return string.contains(charSequence); }}; }


    /**
     * Determines if a string starts with a given prefix string.
     *
     * @param prefix The prefix.
     * @return The predicate.
     */
    public static Predicate<String> startsWith(final String prefix) {
        return prefix == null ? Always.<String>no() : new FalseIfNullOrElse<String>() {
        @Override protected boolean $nullsafe(String string) { return string.startsWith(prefix); }}; }


    /**
     * Determines if a string ends with a given suffix string.
     *
     * @param suffix The suffix.
     * @return The predicate.
     */
    public static Predicate<String> endsWith(final String suffix) {
        return suffix == null ? Always.<String>no() : new FalseIfNullOrElse<String>() {
        @Override protected boolean $nullsafe(String string) { return string.endsWith(suffix); }}; }


    /**
     * Does a {@link String#matches(String) regular expression match} on strings.
     *
     * @param regex The regular expression to use for matching.
     * @return the predicate.
     */
    public static Predicate<String> matches(final String regex) {
        return regex == null ? Always.<String>no() : new FalseIfNullOrElse<String>() {
        @Override protected boolean $nullsafe(String string) { return string.matches(regex); }};}


    /**
     * Create a new strings by prepending a prefix.
     * @param prefix the prefix to prepend
     */
    public static Fn<Object, String> prepend(String prefix) { return Apply.partially(concat).of(prefix); }


    /**
     * Create a new strings by appending a suffix.
     * @param suffix the suffix to append
     */
    public static Fn<Object, String> append(String suffix) { return Apply.partially(argsReversed(concat)).of(suffix); }


    /**
     * Extract substring from strings. As
     * this function simply delegates to {@link String#substring(int, int)}, it
     * may throw an {@link IndexOutOfBoundsException} if the given indexes
     * are invalid.
     *
     * @param beginIndex The index of the first character to include.
     * @param endIndex The index to end the extraction.
     */
    public static Fn<String, String> substring(final int beginIndex, final int endIndex) {
        return new Fn<String, String>() { @Override public String $(String s) { return s.substring(beginIndex, endIndex); }}; }


    /**
     * Get at most a given amount of the first characters of strings.
     * If the string is shorter than the amount, the original string is returned.
     */
    public static Fn<String, String> first(final int charAmount) {
        return new Fn<String, String>() {
            @Override public String $(String s) {
                if (s == null) return "";
                return (charAmount > s.length()) ? s : s.substring(0, charAmount);
            }};
    }


    /**
     * Get at most a given amount of the last characters of strings.
     * If the string is shorter than the amount, the original string is returned.
     */
    public static Fn<String, String> last(final int charAmount) {
        return new Fn<String, String>() {
            @Override public String $(String s) {
                if (s == null) return "";
                return (charAmount > s.length()) ? s : s.substring(s.length() - charAmount, s.length());
            }};
    }


    /**
     * Insert strings in between a prefix and a suffix.
     *
     * @param prefix The prefix to appear before the string.
     * @param suffix The suffix to appear after the string
     */
    public static Fn<Object,String> inBetween(final String prefix, final String suffix) {
        return Base.first(prepend(prefix)).then(append(suffix)); }



    public static Fn<String, String> repeat(final int times) { return new Fn<String, String>() {
        @Override public String $(String s) { return on(asList(s)).repeat(times).join(); }}; }


    private Strings() {}

}
