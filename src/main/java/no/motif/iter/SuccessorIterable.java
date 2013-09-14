package no.motif.iter;

import static no.motif.Singular.optional;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.f.Fn;
import no.motif.single.Optional;

/**
 * Iterable where its elements are computed from an initial element.
 *
 * @param <T> The type of object this iterable yields.
 */
final class SuccessorIterable<T, F extends T> implements Iterable<T>, Serializable {

    private final Optional<F> firstElement;
    private final Fn<? super T,? extends T> successor;

    SuccessorIterable(F firstElement, Fn<? super T, ? extends T> successor) {
        this.firstElement = optional(firstElement);
        this.successor = successor;
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleIterator<T>() {
            Optional<? extends T> next = firstElement;
            @Override
            protected Optional<? extends T> nextIfAvailable() {
                Optional<? extends T> toReturn = next;
                next = next.map(successor);
                return toReturn;
            }};
    }

}
