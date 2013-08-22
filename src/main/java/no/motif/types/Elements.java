package no.motif.types;

import no.motif.f.Predicate;


/**
 * This is a reactor type, gathering the collection operations
 * which is implemented lazily by Motif. Methods that only need
 * to append, prepend, and/or in any way adapt the elements of
 * a collection can accept this type as method argument to ensure,
 * as well as communicating to the callee, that no iterating will
 * take place.
 *
 * @param <T> The type of the contained elements.
 * @see no.motif.iter.PreparedIterable
 */
public interface Elements<T> extends Mappable<T>, Filterable<T>, Appendable<T>, Prependable<T> {


    /**
     * Take only a maximum amount of elements.
     *
     * @param amount the amount to take.
     * @return the elements
     */
    Elements<T> take(int amount);


    /**
     * Take elements as long as a given predicate
     * evaluates to true.
     *
     * @param predicate The predicate, when evaluates an element to false,
     *                  will not include that element, nor any more elements.
     *
     * @return the elements
     */
    Elements<T> takeWhile(Predicate<? super T> predicate);


    /**
     * Take elements until a given predicate evaluates to true.
     *
     * @param predicate The predicate, when evaluates an element to true,
     *                  will not include that element, nor any more elements.
     * @return the elements
     */
    Elements<T> takeUntil(Predicate<? super T> predicate);
}
