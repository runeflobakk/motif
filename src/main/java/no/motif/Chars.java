package no.motif;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.isWhitespace;
import no.motif.f.Predicate;
import no.motif.f.base.FalseIfNull;

/**
 * Functions operating on single {@link Character characters}.
 */
public final class Chars {


    /**
     * Determines if a character is whitespace.
     * @see Character#isWhitespace(char)
     */
    public static final Predicate<Character> whitespace = new FalseIfNull<Character>() {
        @Override protected boolean orElse(Character c) { return isWhitespace(c); }};


    /**
     * Determines if a character is a digit.
     * @see Character#isDigit(char)
     */
    public static final Predicate<Character> digit = new FalseIfNull<Character>() {
        @Override protected boolean orElse(Character c) { return isDigit(c); }};


    /**
     * Determines if a character is a letter.
     * @see Character#isLetter(char)
     */
    public static final Predicate<Character> letter = new FalseIfNull<Character>() {
        @Override protected boolean orElse(Character c) { return isLetter(c); }};


    /**
     * Determines if a character is a letter or a digit.
     * @see Character#isLetterOrDigit(char)
     */
    public static final Predicate<Character> letterOrDigit = new FalseIfNull<Character>() {
        @Override protected boolean orElse(Character c) { return isLetterOrDigit(c); }};


    private Chars() {}
}
