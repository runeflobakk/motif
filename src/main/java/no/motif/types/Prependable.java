package no.motif.types;

/**
 * A container which can have additional elements prepended.
 *
 * @param <T> The type of elements contained.
 */

public interface Prependable<T> {

    /**
     * Prepend one element.
     *
     * @param value the element to prepend.
     * @return the resulting elements.
     */
    Prependable<T> prepend(T value);


    /**
     * Prepend several elements.
     *
     * @param leadingElements the elements to prepend.
     * @return the resulting elements.
     */
    Prependable<T> prepend(Iterable<? extends T> leadingElements);
}
