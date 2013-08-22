package no.motif.types;

/**
 * A container which can have additional elements appended.
 *
 * @param <T> The type of elements contained.
 */
public interface Appendable<T> {

    /**
     * Append one element.
     *
     * @param value the element to append.
     * @return the resulting elements.
     */
    Appendable<T> append(T value);


    /**
     * Append several elements.
     *
     * @param trailingElements the elements to append.
     * @return the resulting elements.
     */
    Appendable<T> append(Iterable<? extends T> trailingElements);

}
