package no.motif.iter;

import static no.motif.Iterate.hasNext;
import static no.motif.Singular.optional;

import java.util.Iterator;

import no.motif.Iterate;
import no.motif.f.Predicate;
import no.motif.option.Optional;

public class TakeWhile<T> implements Iterable<T> {

    private final Predicate<? super T> predicate;
    private final Iterable<T> elements;

    public TakeWhile(Predicate<? super T> predicate, Iterable<T> elements) {
        this.predicate = predicate;
        this.elements = elements;
    }

    @Override public Iterator<T> iterator() {
        return new SimpleIterator<T>() {
            final Iterator<T> iterator = elements.iterator();
            @Override protected Optional<T> nextIfAvailable() {
                return optional(hasNext, iterator).map(predicate, Iterate.<T>next());
            }};
    }

}
