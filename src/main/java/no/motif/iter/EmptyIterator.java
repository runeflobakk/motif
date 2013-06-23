package no.motif.iter;

import java.util.Iterator;
import java.util.NoSuchElementException;

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