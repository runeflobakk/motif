package no.motif.iter;

import static no.motif.Iterate.hasNext;
import static no.motif.Singular.optional;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.Iterate;
import no.motif.f.Predicate;
import no.motif.option.Optional;

class PredicateBoundedIterable<T> implements Iterable<T>, Serializable {

    private final Predicate<? super T> predicate;
    private final Iterable<T> elements;

    PredicateBoundedIterable(Predicate<? super T> predicate, Iterable<T> elements) {
        this.predicate = predicate;
        this.elements = elements;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleIterator<T>() {
            final Iterator<T> iterator = elements.iterator();
            @Override protected Optional<T> nextIfAvailable() {
                return optional(hasNext, iterator).map(predicate, Iterate.<T>next());
            }};
    }

}
