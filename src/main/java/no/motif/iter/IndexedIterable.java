package no.motif.iter;

import static no.motif.Iterate.hasNext;
import static no.motif.Singular.optional;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.Iterate;
import no.motif.single.Elem;
import no.motif.single.Optional;

class IndexedIterable<T> implements Iterable<Elem<T>>, Serializable {

    private final Iterable<T> elements;
    private final int startIndex;

    public IndexedIterable(int startIndex, Iterable<T> elements) {
        this.startIndex = startIndex;
        this.elements = elements;
    }

    @Override
    public Iterator<Elem<T>> iterator() {
        return new SimpleIterator<Elem<T>>() {
            Iterator<T> iterator = elements.iterator();
            int index = startIndex;
            @Override
            protected Optional<Elem<T>> nextIfAvailable() {
                return optional(hasNext, iterator).map(Iterate.<T>next()).map(Elem.<T>withIndex(index++));
            }};
    }

}
