package no.motif;

import static java.lang.Character.isDigit;
import no.motif.f.Predicate;

public final class Chars {

    public static final Predicate<Character> digit = new Predicate<Character>() {
        @Override public boolean $(Character c) { return c != null && isDigit(c); }};

    private Chars() {} static { new Chars(); }
}
