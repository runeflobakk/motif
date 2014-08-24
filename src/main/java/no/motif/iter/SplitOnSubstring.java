package no.motif.iter;

import static no.motif.Base.equalOrGreaterThan;
import static no.motif.Singular.none;
import static no.motif.Singular.optional;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.single.Optional;

public class SplitOnSubstring implements Iterable<String>, Serializable {

    private final String string;
    private final int stringLength;

    private final String substring;
    private final int substringLength;

    public SplitOnSubstring(String string, String splittingSubstring) {
        this.string = string;
        this.stringLength = string.length();
        this.substring = splittingSubstring;
        this.substringLength = splittingSubstring.length();
    }


    @Override
    public Iterator<String> iterator() {
        return new SimpleIterator<String>() {
            int pos = 0;
            @Override
            protected Optional<? extends String> nextIfAvailable() {
                if (pos > stringLength) return none();
                int endIndex = optional(equalOrGreaterThan(0), string.indexOf(substring, pos)).orElse(stringLength);
                String next = string.substring(pos, endIndex);
                pos = endIndex + substringLength;
                return optional(next);
            }
        };
    }
}
