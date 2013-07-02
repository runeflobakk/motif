package no.motif;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.isWhitespace;
import no.motif.f.FalseIfNullOrElse;
import no.motif.f.Predicate;

public final class Chars {

    public static final Predicate<Character> digit = new FalseIfNullOrElse<Character>() {
        @Override protected boolean $nullsafe(Character c) { return isDigit(c); }};

    public static final Predicate<Character> whitespace = new FalseIfNullOrElse<Character>() {
        @Override protected boolean $nullsafe(Character c) { return isWhitespace(c); }};

    public static final Predicate<Character> letter = new FalseIfNullOrElse<Character>() {
        @Override protected boolean $nullsafe(Character c) { return isLetter(c); }};

    public static final Predicate<Character> letterOrDigit = new FalseIfNullOrElse<Character>() {
        @Override protected boolean $nullsafe(Character c) { return isLetterOrDigit(c); }};


    private Chars() {}
}
