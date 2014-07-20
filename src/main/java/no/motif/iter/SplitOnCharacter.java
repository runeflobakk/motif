package no.motif.iter;

import static no.motif.Singular.none;
import static no.motif.Singular.optional;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.f.Predicate;
import no.motif.single.Optional;

public class SplitOnCharacter implements Iterable<String>, Serializable {

    private final String string;
    private final int stringLength;
    private final Predicate<? super Character> splittingCharacter;

    public SplitOnCharacter(String string, Predicate<? super Character> splittingCharacter) {
        this.string = string;
        this.stringLength = string.length();
        this.splittingCharacter = splittingCharacter;
    }

    @Override
    public Iterator<String> iterator() {
        return new SimpleIterator<String>() {
            int pos = 0;
            @Override
            protected Optional<? extends String> nextIfAvailable() {
                if (pos > stringLength) return none();
                int endIndex = indexOf(splittingCharacter, string, pos);
                String next = string.substring(pos, endIndex);
                pos = endIndex + 1;
                return optional(next);
            }
        };
    }

    private static int indexOf(Predicate<? super Character> character, String string, int startIndex) {
        for ( ; startIndex < string.length() && !character.$(string.charAt(startIndex)); startIndex++);
        return startIndex;
    }
}
