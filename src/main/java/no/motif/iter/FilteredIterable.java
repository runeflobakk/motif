package no.motif.iter;

import static no.motif.Singular.none;
import static no.motif.Singular.the;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.f.Predicate;
import no.motif.single.Optional;

final class FilteredIterable<T> implements Iterable<T>, Serializable {

    private final Iterable<T> elements;
    private final Predicate<? super T> accepts;

    FilteredIterable(Iterable<T> elements, Predicate<? super T> filter) {
        this.elements = elements;
        this.accepts = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleIterator<T>() {
            final Iterator<T> iterator = elements.iterator();

            @Override
            protected Optional<T> nextIfAvailable() {
                while (iterator.hasNext()) {
                    T next = iterator.next();
                    if (accepts.$(next)) return the(next).asOptional();
                }
                return none();
            }
        };
    }

}
