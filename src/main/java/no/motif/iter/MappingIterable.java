package no.motif.iter;

import java.util.Iterator;

import no.motif.f.Fn;

final class MappingIterable<I, O> implements Iterable<O> {

    private final Iterable<I> elements;

    private final Fn<? super I, O> map;

    MappingIterable(Iterable<I> elements, Fn<? super I, O> map) {
        this.elements = elements;
        this.map = map;
    }

    @Override
    public Iterator<O> iterator() {
        return new ReadOnlyIterator<O>() {
            final Iterator<I> iterator = elements.iterator();
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public O next() {
                return map.$(iterator.next());
            }
        };
    }

}
