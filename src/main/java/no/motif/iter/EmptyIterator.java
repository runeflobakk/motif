package no.motif.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * An iterator which is empty, i.e.
 * {@link #hasNext()} always returns false, and {@link #next()}
 * throws {@link NoSuchElementException}.
 *
 * Obtain an instance using {@link #instance() EmptyIterator.instance()}.
 */
public final class EmptyIterator<T> extends ReadOnlyIterator<T> {

    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> instance() {
        return (Iterator<T>) EMPTY;
    }

    private static final Iterator<?> EMPTY = new EmptyIterator<Object>();


    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        throw new NoSuchElementException();
    }

    private EmptyIterator() { }
}