package no.motif.iter;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.Singular;
import no.motif.option.Optional;

class ConcatenatedIterable<T> implements Iterable<T>, Serializable {

    private final Iterable<? extends T> leadingElements;
    private final Iterable<? extends T> trailingElements;

    ConcatenatedIterable(Iterable<? extends T> leadingElements, Iterable<? extends T> trailingElements) {
        this.leadingElements = leadingElements;
        this.trailingElements = trailingElements;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleIterator<T>() {

            Iterator<? extends T> iterator = leadingElements.iterator();
            boolean exhausted;

            @Override
            protected Optional<T> nextIfAvailable() {
                if (iterator.hasNext()) return Singular.<T>optional(iterator.next());
                else if (exhausted) return Singular.<T>none();
                else {
                    iterator = trailingElements.iterator();
                    exhausted = true;
                    return nextIfAvailable();
                }
            }
        };
    }

}
