package no.motif.iter;

import static no.motif.Singular.none;
import static no.motif.Singular.optional;

import java.util.Iterator;

import no.motif.Iterate;
import no.motif.option.Optional;

class SkipLeadingIterable<T> implements Iterable<T> {

    private final Iterable<T> elements;
    private final int toSkip;

    SkipLeadingIterable(int skip, Iterable<T> elements) {
        this.toSkip = skip;
        this.elements = elements;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleIterator<T>() {
            Iterator<T> iterator = elements.iterator();
            int skipped = 0;
            @Override
            protected Optional<T> nextIfAvailable() {
                if (!iterator.hasNext()) {
                    return none();
                } else if (skipped == toSkip) {
                    return optional(Iterate.hasNext, iterator).map(Iterate.<T>next());
                } else {
                    iterator.next();
                    skipped++;
                    return nextIfAvailable();
                }
            }
        };
    }
}
