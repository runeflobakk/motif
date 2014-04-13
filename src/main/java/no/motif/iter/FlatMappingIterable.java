package no.motif.iter;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.f.Fn;

class FlatMappingIterable<I, O> implements Iterable<O>, Serializable {


    private final Iterable<I> elements;
    private final Fn<? super I, ? extends Iterable<O>> fn;

    public FlatMappingIterable(Iterable<I> elements, Fn<? super I, ? extends Iterable<O>> fn) {
        this.elements = elements;
        this.fn = fn;
    }

    @Override
    public Iterator<O> iterator() {
        return new FlatMappingIterator();
    }

    private final class FlatMappingIterator extends ReadOnlyIterator<O> {
        Iterator<I> iterator = elements.iterator();
        Iterator<O> mapped = EmptyIterator.<O>instance();

        @Override
        public boolean hasNext() {
            if (!mapped.hasNext() && iterator.hasNext()) {
                mapped = fn.$(iterator.next()).iterator();
            }
            return mapped.hasNext();
        }

        @Override
        public O next() {
            return mapped.next();
        }
    }

}
