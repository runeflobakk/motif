package no.motif.iter;

import static no.motif.Exceptions.asRuntimeException;
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
 * In addition, implementations are free to throw any exception, which,
 * if is a checked exception, will be rethrown wrapped in a <code>RuntimeException</code>.
 *
 * @param <T> The type of elements yielded by this iterator.
 * @see #nextIfAvailable()
 */
public abstract class SimpleIterator<T> extends ReadOnlyIterator<T> {

    private Optional<? extends T> next = none();

    /**
     * @return The next element if any, or {@link Singular#none() none} if there are no more elements.
     */
    protected abstract Optional<? extends T> nextIfAvailable() throws Exception;

    @Override
    public final boolean hasNext() {
        try {
            next = nextIfAvailable();
        } catch (Exception e) {
            throw asRuntimeException(e);
        }
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
