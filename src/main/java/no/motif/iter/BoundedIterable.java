package no.motif.iter;

import static no.motif.Singular.optional;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.Singular;
import no.motif.option.Optional;

class BoundedIterable<T> implements Iterable<T>, Serializable {

    private final Iterable<T> elements;
    private final int maxAmount;

    public BoundedIterable(int maxAmount, Iterable<T> elements) {
        this.maxAmount = maxAmount;
        this.elements = elements;}

    @Override public Iterator<T> iterator() {
        return new SimpleIterator<T>() {
            int returned = 0;
            final Iterator<T> iterator = elements.iterator();
            @Override protected Optional<T> nextIfAvailable() {
                return returned++ < maxAmount && iterator.hasNext()? optional(iterator.next()) : Singular.<T>none();
            }};
    }

}
