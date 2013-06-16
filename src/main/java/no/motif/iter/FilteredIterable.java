package no.motif.iter;

import java.util.Iterator;

import no.motif.f.Predicate;

class FilteredIterable<T> implements Iterable<T> {

    private final Iterable<T> elements;
    private final Predicate<? super T> accepts;

    FilteredIterable(Iterable<T> elements, Predicate<? super T> filter) {
        this.elements = elements;
        this.accepts = filter;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReadOnlyIterator<T>() {
            final Iterator<T> iterator = elements.iterator();
            T next;
            @Override
            public boolean hasNext() {
                while (iterator.hasNext()) {
                    next = iterator.next();
                    if (accepts.$(next)) return true;
                }
                return false;
            }

            @Override
            public T next() {
                return next;
            }
        };
    }

}
