package no.motif.iter;

import static no.motif.Base.greaterThan;
import static no.motif.Base.where;
import static no.motif.Singular.none;
import static no.motif.Singular.optional;
import static no.motif.Strings.length;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.single.Optional;

public class SplitOnSubstring implements Iterable<String>, Serializable {

    private final String string;
    private final String substring;

    public SplitOnSubstring(String string, String splittingSubstring) {
        this.string = string;
        this.substring = splittingSubstring;
    }

    private String getCandidateAt(int pos) {
        return string.substring(pos, pos + substring.length());
    }

    @Override
    public Iterator<String> iterator() {
        return new SimpleIterator<String>() {
            int pos = 0;
            @Override
            protected Optional<? extends String> nextIfAvailable() {
                if (pos >= string.length()) return none();
                getStart();
                if (pos >= string.length()) return none();
                int nextDelimiter = string.indexOf(substring, pos);
                String next = (nextDelimiter == -1) ? string.substring(pos) : string.substring(pos, nextDelimiter);
                pos = nextDelimiter;
                return optional(where(length, greaterThan(0)), next);
            }

            private int getStart() {
                if (pos >= string.length() - substring.length()) return pos;
                for (String candidate = getCandidateAt(pos);
                     pos <= string.length() - substring.length() && substring.equals(candidate);
                     candidate = getCandidateAt(++pos));
                return pos;
            }

            private int forwardToSplitter() {
                for (String candidate = getCandidateAt(pos);
                     pos < string.length() && !substring.equals(candidate);
                     candidate = getCandidateAt(++pos));
                return pos;
            }

        };
    }
}
