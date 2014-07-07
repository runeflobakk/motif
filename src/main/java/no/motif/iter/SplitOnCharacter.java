package no.motif.iter;

import static no.motif.Base.greaterThan;
import static no.motif.Base.not;
import static no.motif.Base.where;
import static no.motif.Singular.none;
import static no.motif.Singular.optional;
import static no.motif.Strings.length;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.f.Predicate;
import no.motif.single.Optional;

public class SplitOnCharacter implements Iterable<String>, Serializable {

    private final String string;
    private final Predicate<Character> splittingCharacter;

    public SplitOnCharacter(String string, Predicate<Character> splittingCharacter) {
        this.string = string;
        this.splittingCharacter = splittingCharacter;
    }

    @Override
    public Iterator<String> iterator() {
        return new SimpleIterator<String>() {
            int pos = 0;
            @Override
            protected Optional<? extends String> nextIfAvailable() {
                if (pos >= string.length()) return none();
                int start = forwardTo(not(splittingCharacter));
                return optional(where(length, greaterThan(0)), string.substring(start, forwardTo(splittingCharacter)));
            }

            private int forwardTo(Predicate<Character> matchingCharacter) {
                for ( ; pos < string.length() && !matchingCharacter.$(string.charAt(pos)); pos++);
                return pos;
            }
        };
    }
}
