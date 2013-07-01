package no.motif;

import static java.lang.Character.isDigit;
import no.motif.f.FalseIfNullOrElse;
import no.motif.f.Predicate;

public final class Chars {

    public static final Predicate<Character> digit = new FalseIfNullOrElse<Character>() {
        @Override protected boolean $nullsafe(Character c) { return isDigit(c); }};

    private Chars() {}
}
