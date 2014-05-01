package no.motif.iter;

/**
 * Base implementation for iterating over elements retrieved using an
 * incrementing index, where the start and end indexes are known beforehand.
 * E.g. characters of a String, or elements of an array.
 */
public abstract class PreIndexedContentIterator<T> extends ReadOnlyIterator<T> {

    private final int endIndex;
    private int next;

    /**
     * @param endIndex The end index, exclusively.
     */
    public PreIndexedContentIterator(int endIndex) {
        this(0, endIndex);
    }

    /**
     * @param startIndex The first index, inclusively
     * @param endIndex The end index, exclusively.
     */
    public PreIndexedContentIterator(int startIndex, int endIndex) {
        if (endIndex < startIndex) throw new IllegalArgumentException(
            "endIndex must be same or more than startIndex. startIndex=" + startIndex + ", endIndex=" + endIndex);
        this.next = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public final boolean hasNext() {
        return next < endIndex;
    }

    @Override
    public final T next() {
        if (hasNext()) return elementAt(next++);
        else throw new IndexOutOfBoundsException(String.valueOf(next));
    }

    /**
     *
     * @param index The index of the element to yield. Guarantied to never
     *              be out of bounds with respect to the start and end indexes.
     *              E.g. if the given start and end indexes are 0 and 4, this
     *              method will be called for each of the indexes 0, 1, 2, and 3.
     *
     * @return The element for the given index.
     */
    protected abstract T elementAt(int index);
}
