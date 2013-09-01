package no.motif.iter;

import static no.motif.Singular.none;

import java.util.Iterator;
import java.util.NoSuchElementException;

import no.motif.Singular;
import no.motif.single.Optional;

/**
 * SimpleIterator offers an easier way to implement the
 * {@link java.lang.Iterator} interface, by requiring only
 * one method, {@link #nextIfAvailable()}, to be implemented instead of both
 * {@link Iterator#hasNext() hasNext()} and {@link Iterator#next() next()}.
 *
 * @param <T> The type of elements yielded by this iterator.
 * @see #nextIfAvailable()
 */
public abstract class SimpleIterator<T> extends ReadOnlyIterator<T> {

    private Optional<T> next = none();

    /**
     * @return The next element if any, or {@link Singular#none() none} if there are no more elements.
     */
    protected abstract Optional<T> nextIfAvailable();

    @Override
    public final boolean hasNext() {
        next = nextIfAvailable();
        return next.isSome();
    }

    @Override
    public final T next() {
        if (!next.isSome() && !hasNext()) throw new NoSuchElementException();
        T toReturn = next.get();
        next = none();
        return toReturn;
    }

}
