package no.motif.iter;

/**
 * Wraps a single object in an iterator. The object is returned at most
 * once from {@link #next()}.
 */
public final class SingularIterator<V> extends ReadOnlyIterator<V> {

    private final V value;
    private boolean hasBeenReturned = false;

    public SingularIterator(V value) {
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        return !hasBeenReturned;
    }

    @Override
    public V next() {
        if (hasBeenReturned) throw new UnsupportedOperationException();
        hasBeenReturned = true;
        return value;
    }

}