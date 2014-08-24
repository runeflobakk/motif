package no.motif.iter;

import static no.motif.Singular.none;
import static no.motif.Singular.optional;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.single.Optional;

public class CyclingIterable<T> implements Iterable<T>, Serializable {

    private final Iterable<T> elements;
    private final int times;

    public CyclingIterable(int times, Iterable<T> elements) {
        this.times = times;
        this.elements = elements;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleIterator<T>() {
            Iterator<T> iterator = elements.iterator();
            int cycle = 0;

            @Override
            protected Optional<? extends T> nextIfAvailable() {
                if (iterator.hasNext()) return optional(iterator.next());
                else if (++cycle == times) return none();
                else {
                    iterator = elements.iterator();
                    return nextIfAvailable();
                }
            }
        };
    }

}
