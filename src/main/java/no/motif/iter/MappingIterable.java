package no.motif.iter;

import static no.motif.Iterate.hasNext;
import static no.motif.Singular.optional;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.Iterate;
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
                return optional(hasNext, iterator).map(Iterate.<I>next()).map(map);
            }
        };
    }

}
