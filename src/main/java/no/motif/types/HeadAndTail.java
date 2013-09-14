package no.motif.types;

import java.util.NoSuchElementException;

import no.motif.Singular;
import no.motif.single.Optional;

/**
 * A recursive type which can be splitted into a head element,
 * and the remaining tail, which may be splitted further into
 * head and tail.
 *
 * @param <E> The type of objects which may be retrieved from the head element.
 */
public interface HeadAndTail<E> {

    /**
     * Gets the head, or first element, if it exists.
     */
    Optional<E> head();

    /**
     * Gets the tail, i.e. the remainder after the head.
     *
     * @return The tail, which may be empty if there is only a head element.
     * @throws NoSuchElementException if {@link #head()} is {@link Singular#none() none}, so
     *                                there is no remainder.
     */
    HeadAndTail<E> tail();

}
