package no.motif;

import static no.motif.Base.all;
import static no.motif.Base.always;
import static no.motif.Base.alwaysThrow;
import static no.motif.Base.both;
import static no.motif.Base.equalTo;
import static no.motif.Base.exists;
import static no.motif.Base.not;
import static no.motif.Base.notNull;
import static no.motif.Base.when;
import static no.motif.Base.where;
import static no.motif.Chars.digit;
import static no.motif.Chars.letter;
import static no.motif.Chars.letterOrDigit;
import static no.motif.Chars.whitespace;
import static no.motif.Exceptions.asRuntimeException;
import static no.motif.Ints.add;
import static no.motif.Iterate.on;
import static no.motif.Singular.optional;
import static no.motif.f.Apply.argsReversed;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

import no.motif.f.Apply;
import no.motif.f.Fn;
import no.motif.f.Fn2;
import no.motif.f.Predicate;
import no.motif.f.Predicate.Always;
import no.motif.f.base.FalseIfNull;
import no.motif.iter.SplitOnCharacter;
import no.motif.single.Optional;

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
     * Converts a string to a <code>long</code> value using {@link Long#valueOf(String)}.
     * If the string is <code>null</code>, 0 is yielded.
     */
    public static final Fn<String, Long> toLong = new Fn<String, Long>() {
        @Override public Long $(String numeric) { return numeric != null ? Long.valueOf(numeric) : 0; }};


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
    public static final Fn<String, Iterable<Byte>> bytes = when(notNull, new Fn<String, Iterable<Byte>>() {
        @Override public Iterable<Byte> $(String s) {
            try {
                return Iterate.on(s.getBytes(Implicits.getEncoding()));
            } catch (UnsupportedEncodingException e) {
                throw asRuntimeException(e);
            }
        }
    }).orElse(Iterate.<Byte>none());




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
    public static final Fn<String, String> trimmed = when(notNull, new Fn<String, String>() {
        @Override public String $(String s) { return s.trim(); }});


    /**
     * Convert a string to {@link String#toLowerCase() lower case}.
     */
    public static final Fn<String, String> lowerCased = when(notNull, new Fn<String, String>() {
        @Override public String $(String s) { return s.toLowerCase(Implicits.getLocale()); }});


    /**
     * Convert a string to {@link String#toUpperCase() upper case}
     */
    public static final Fn<String, String> upperCased = when(notNull, new Fn<String, String>() {
        @Override public String $(String s) { return s.toUpperCase(Implicits.getLocale()); }});


    /**
     * Gives the length of a string, i.e. the amount of characters. <code>null</code>
     * yields length 0.
     */
    public static final Fn<String, Integer> length = when(notNull, new Fn<String, Integer>() {
        @Override public Integer $(String s) { return s.length(); }}).orElse(0);


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
    public static final Fn<String, String> reversed = when(notNull, new Fn<String, String>() {
        @Override public String $(String s) { return new StringBuilder(s).reverse().toString(); }});




    /**
     * Determines if a substring is present in a string. A string never contains <code>null</code>,
     * nor does <code>null</code> contain any substring.
     *
     * @param charSequence The substring to find.
     * @return The predicate.
     */
    public static Predicate<String> contains(final CharSequence charSequence) {
        return charSequence == null ? Always.<String>no() : new FalseIfNull<String>() {
        @Override protected boolean orElse(String string) { return string.contains(charSequence); }}; }


    /**
     * Determines if a string starts with a given prefix string.
     *
     * @param prefix The prefix.
     * @return The predicate.
     */
    public static Predicate<String> startsWith(final String prefix) {
        return prefix == null ? Always.<String>no() : new FalseIfNull<String>() {
        @Override protected boolean orElse(String string) { return string.startsWith(prefix); }}; }


    /**
     * Determines if a string ends with a given suffix string.
     *
     * @param suffix The suffix.
     * @return The predicate.
     */
    public static Predicate<String> endsWith(final String suffix) {
        return suffix == null ? Always.<String>no() : new FalseIfNull<String>() {
        @Override protected boolean orElse(String string) { return string.endsWith(suffix); }}; }


    /**
     * Does a {@link String#matches(String) regular expression match} on strings.
     *
     * @param regex The regular expression to use for matching.
     * @return the predicate.
     */
    public static Predicate<String> matches(final String regex) {
        return regex == null ? Always.<String>no() : new FalseIfNull<String>() {
        @Override protected boolean orElse(String string) { return string.matches(regex); }};}


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
        if (beginIndex < 0 || endIndex < 0)
            return alwaysThrow(new StringIndexOutOfBoundsException(
                    "Cannot extract substring using negative index. " +
                    "beginIndex: " + beginIndex + ", endIndex: " + endIndex));
        return substring(always(beginIndex), always(endIndex));
    }

    public static Fn<String, String> substring(final Fn<? super String, Integer> beginIndex, final Fn<? super String, Integer> endIndex) {
        return when(notNull, new Fn<String, String>() { @Override public String $(String s) {
            return optional(s).map(before(endIndex)).map(from(beginIndex)).orNull();
        }});
    }


    /**
     * Get at most a given amount of the first characters of strings.
     * If the string is shorter than the amount, the original string is returned.
     */
    public static Fn<String, String> first(final int charAmount) {
        return when(notNull, new Fn<String, String>() { @Override public String $(String s) {
                    return (charAmount > s.length()) ? s : s.substring(0, charAmount);
                }}).orElse("");
    }


    /**
     * Get at most a given amount of the last characters of strings.
     * If the string is shorter than the amount, the original string is returned.
     */
    public static Fn<String, String> last(final int charAmount) {
        return when(notNull, new Fn<String, String>() { @Override public String $(String s) {
                    return (charAmount > s.length()) ? s : s.substring(s.length() - charAmount, s.length());
                }}).orElse("");
    }


    /**
     * Insert strings in between a prefix and a suffix.
     *
     * @param prefix The prefix to appear before the string.
     * @param suffix The suffix to appear after the string
     */
    public static Fn<Object,String> inBetween(final String prefix, final String suffix) {
        return Base.first(prepend(prefix)).then(append(suffix)); }


    /**
     * Repeats a string a given amount of times.
     *
     * @param times The amount of times to repeat the string.
     */
    public static Fn<String, String> repeat(final int times) { return when(notNull, new Fn<String, String>() {
        @Override public String $(String s) { return on((Object) s).repeat(times).join(); }}); }


    /**
     * Repeats a string, insterting given separator, a given amount of times.
     *
     * @param times The amount of times to repeat the string.
     * @param separator The separator string to insert between the repeating strings.
     */
    public static Fn<String, String> repeat(final int times, final String separator) {
        return when(notNull, new Fn<String, String>() {
            @Override public String $(String s) { return on((Object) s).repeat(times).join(separator); }}); }



    /**
     * Inside strings, searches for the <em>first</em> occurence of a substring, and yields the
     * rest of the string <em>after</em> the substring occurence, not including the
     * substring itself.
     * <p>
     * Passing <code>null</code> to the {@link Fn} always yields <code>null</code>
     * </p>
     * <p>
     * If the substring is not found (or it is <code>null</code>), then <code>null</code> is returned.
     * </p><p>
     * If the substring is the empty string, the original string is returned.
     * </p>
     *
     * @param substring the substring to search for.
     */
    public static Fn<String, String> after(final String substring) {
        if (substring == null || substring.isEmpty()) return NOP.fn();
        return from(Base.first(indexOf(substring)).then(add(substring.length())));
    }


    /**
     * Inside strings, searches for the <em>last</em> occurence of a substring, and yields the
     * rest of the string <em>after</em> the substring occurence, not including the
     * substring itself.
     * <p>
     * Passing <code>null</code> to the {@link Fn} always yields <code>null</code>
     * </p>
     * <p>
     * If the substring is not found, or if it is <code>null</code>, then <code>null</code> is returned.
     * If the substring is empty, the empty string is returned.
     * </p>
     *
     * @param substring the substring to search for.
     */
    public static Fn<String, String> afterLast(final String substring) {
        if (substring == null || substring.isEmpty()) return when(notNull, Base.<String, String, String>always(""));
        return from(Base.first(lastIndexOf(substring)).then(add(substring.length())));
    }



    /**
     * Yields substrings <em>from</em> a position index.
     * @see #from(Fn)
     */
    public static Fn<String, String> from(int index) { return from(always(index)); }



    /**
     * Yields substrings <em>from</em> a position index. If the given index
     * {@link Fn} yields <code>null</code>, then <code>null</code> is returned.
     * If a positive index out of bounds with the length of the string
     * is yielded, the empty string is returned.
     *
     * <p>A negative index value is invalid and will throw an {@link StringIndexOutOfBoundsException}.</p>
     * <p>Passing the <code>null</code>-String always yields <code>null</code>.</p>
     *
     * @param index The {@link Fn} to resolve the index.
     */
    public static Fn<String, String> from(final Fn<? super String, Integer> index) {
        return when(notNull, new Fn<String, String>() {
            @Override
            public String $(String s) {
                Integer idx = index.$(s);
                if (idx == null) return null;
                if (idx >= s.length()) return "";
                return s.substring(idx);
            }});
    }





    /**
     * Inside strings, searches for the <em>first</em> occurence of a substring, and yields the
     * the string <em>before</em> the substring occurence, not including the
     * substring itself.
     * <p>
     * Passing <code>null</code> to the {@link Fn} always yields <code>null</code>
     * </p>
     * <p>
     * If the substring is <code>null</code>, the original string is returned.
     * If the substring is not found, <code>null</code> is returned.
     * </p><p>
     * If the substring is the empty string, or found from the beginning of the string, the empty string is returned.
     * </p>
     *
     * @param substring the substring to search for.
     */
    public static Fn<String, String> before(final String substring) {
        return substring == null ? NOP.<String>fn() : before(indexOf(substring));
    }


    /**
     * Inside strings, searches for the <em>last</em> occurence of a substring, and yields the
     * the string <em>before</em> the substring occurence, not including the
     * substring itself.
     * <p>
     * Passing <code>null</code> to the {@link Fn} always yields <code>null</code>
     * </p><p>
     * If the substring is <code>null</code> or empty, the original string is returned.
     * If the substring is not found, <code>null</code> is returned.
     * </p>
     *
     * @param substring the substring to search for.
     */
    public static Fn<String, String> beforeLast(final String substring) {
        return substring != null ? before(lastIndexOf(substring)) : NOP.<String>fn();
    }


    /**
     * Yields substrings <em>before</em> a position index.
     * @see #before(Fn)
     */
    public static Fn<String, String> before(int index) { return before(always(index)); }


    /**
     * Yields substrings <em>before</em> a position index. If the given index
     * {@link Fn} yields <code>null</code>, or a positive index out of bounds
     * with the length of the string, the original string is returned.
     *
     * <p>A negative index is invalid and will throw an {@link StringIndexOutOfBoundsException}.</p>
     * <p>Passing the <code>null</code>-String always yields <code>null</code>.</p>
     *
     * @param index The {@link Fn} to resolve the index.
     */
    public static Fn<String, String> before(final Fn<? super String, Integer> index) {
        return when(notNull, new Fn<String, String>() {
            @Override
            public String $(String s) {
                Integer idx = index.$(s);
                if (idx == null) return null;
                if (idx >= s.length()) return s;
                return s.substring(0, idx);
            }});
    }


    /**
     * Yield the string _between_ two substrings. The substrings will be the first possible
     * matches, which means <code>between("x", "y")</code> applied to the string
     * <code>"xxyy"</code> will yield <code>"x"</code>.
     *
     * @param openSubstring
     * @param closeSubstring
     */
    public static Fn<String,String> between(String openSubstring, String closeSubstring) {
        if (openSubstring == null || closeSubstring == null) return always(null);
        return Base.first(after(openSubstring)).then(before(closeSubstring));
    }


    /**
     * Yield the string _between_ two outermost substrings. This means
     * <code>betweenOuter("x", "y")</code> applied to the string
     * <code>"xxyy"</code> will yield <code>"xy"</code>.
     *
     * @param openSubstring
     * @param closeSubstring
     */
    public static Fn<String,String> betweenOuter(String openSubstring, String closeSubstring) {
        if (openSubstring == null || closeSubstring == null) return always(null);
        return Base.first(after(openSubstring)).then(beforeLast(closeSubstring));
    }


    /**
     * Yield all strings occurring _between_ two substrings.
     *
     * @param openSubstring
     * @param closeSubstring
     */
    public static Fn<String, Iterable<String>> allBetween(final String openSubstring, final String closeSubstring) {
        if (openSubstring == null || closeSubstring == null) return always((Iterable<String>) Collections.<String>emptySet());
        if ("".equals(openSubstring) && "".equals(closeSubstring)) return alwaysThrow(new IllegalArgumentException(
                "Extracting all strings between two empty strings would yield an infinite amount of empty strings!"));
        return when(notNull, new Fn<String, Iterable<String>>() {
            final Fn<String, String> firstSubstring = between(openSubstring, closeSubstring);
            @Override
            public Iterable<String> $(String s) {
                Optional<String> original = optional(s);
                Optional<String> first = original.map(firstSubstring);
                if (!first.isSome()) return Collections.emptySet();
                Optional<String> rest = original.map(nonblank, after(first.map(inBetween(openSubstring, closeSubstring)).orElse(null)));
                return first.append(this.$(rest.orElse("")));
            }});
    }



    /**
     * Yields index position of first occurence of a <code>char</code>, or <code>null</code>
     * if the <code>char</code> cannot be found.
     *
     * @see String#indexOf(int)
     */
    public static Fn<String, Integer> indexOf(final char c) {
        return when(notNull, new Fn<String, Integer>() {
            @Override public Integer $(String s) {
                int index = s.indexOf(c);
                return index >= 0 ? index : null;
            }});
    }


    /**
     * Yields index position of last occurence of a <code>char</code>, or <code>null</code>
     * if the <code>char</code> cannot be found.
     *
     * @see String#indexOf(int)
     */
    public static Fn<String, Integer> lastIndexOf(final char c) { return when(notNull, new Fn<String, Integer>() {
        @Override public Integer $(String s) {
            int index = s.lastIndexOf(c);
            return index >= 0 ? index : null;
        }});
    }


    /**
     * Yields index position of first occurence of a substring, or <code>null</code>
     * if the substring cannot be found.
     *
     * @see String#indexOf(String)
     */
    public static Fn<String, Integer> indexOf(final String substring) {
        if (substring == null) return always(null);
        return when(notNull, new Fn<String, Integer>() {
            @Override public Integer $(String s) {
                int index = s.indexOf(substring);
                return index >= 0 ? index : null;
            }});
    }



    /**
     * Yields index position of last occurence of a substring, or <code>null</code>
     * if the substring cannot be found.
     *
     * @see String#indexOf(String)
     */
    public static Fn<String, Integer> lastIndexOf(final String substring) {
        if (substring == null) return always(null);
        return when(notNull, new Fn<String, Integer>() {
            @Override public Integer $(String s) {
                int index = s.lastIndexOf(substring);
                return index >= 0 ? index : null;
            }});
    }


    /**
     * Split a string on each occurence of a <code>char</code> delimiter.
     * The splitting character is not included in the resulting strings, and any
     * consecutive occurrences of the character are treated as one delimiter instance.
     *
     * @param character the delimiter character
     */
    public static Fn<String, Iterable<String>> split(char character) { return split(equalTo(character)); }


    /**
     * Split a string into several on any character passing the given
     * <code>Character</code> predicate.
     * The characters accepted by the predicate is not included in the
     * resulting strings, and any consecutive accepted characters are
     * treated as one delimiter instance.
     *
     * @param character the predicate which decides if a character is
     *                  a delimiter.
     */
    public static Fn<String, Iterable<String>> split(final Predicate<Character> character) {
        return new Fn<String, Iterable<String>>() {
            @Override public Iterable<String> $(String string) {
                return new SplitOnCharacter(string, character);
            }};
    }


    private Strings() {}
}
