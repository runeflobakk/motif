package no.motif.iter;

import java.util.Iterator;

/**
 * Convenient base class for {@link Iterator}s not meant to remove
 * objects while iterating.
 *
 * @param <T> Type of objects this iterator yields.
 */
public abstract class ReadOnlyIterator<T> implements Iterator<T> {

    /**
     * Method is not supported and throws {@link UnsupportedOperationException}
     * if called.
     */
    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }

}
