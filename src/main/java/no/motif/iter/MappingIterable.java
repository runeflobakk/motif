package no.motif.iter;

import static no.motif.Singular.the;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.Singular;
import no.motif.f.Fn;
import no.motif.single.Optional;

final class MappingIterable<I, O> implements Iterable<O>, Serializable {

    private final Iterable<I> elements;

    private final Fn<? super I, O> map;

    MappingIterable(Iterable<I> elements, Fn<? super I, O> map) {
        this.elements = elements;
        this.map = map;
    }

    @Override
    public Iterator<O> iterator() {
        return new SimpleIterator<O>() {
            final Iterator<I> iterator = elements.iterator();

            @Override
            protected Optional<O> nextIfAvailable() {
                return iterator.hasNext() ? the(map.$(iterator.next())).asOptional() : Singular.<O>none();
            }
        };
    }

}
