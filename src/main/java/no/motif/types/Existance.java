package no.motif.types;

import no.motif.f.Predicate;

/**
 * A container type which can evaluate for existance of its
 * element(s), as well as evaluate if it is empty.
 *
 * @param <T> The type of the contained element(s)
 */
public interface Existance<T> {

    /**
     * @return <code>true</code> if empty, <code>false</code> otherwise.
     */
    boolean isEmpty();


    /**
     * Decide if an element exist.
     *
     * @param predicate evaluates the element(s).
     * @return <code>true</code> once the predicate evaluates to true,
     *         otherwise <code>false</code>.
     */
    boolean exists(Predicate<? super T> predicate);

}
