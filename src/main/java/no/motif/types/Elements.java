package no.motif.types;

import java.util.NoSuchElementException;

import no.motif.Singular;
import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.single.Optional;

/**
 * This is the main type of Motif's collection API and
 * encompasses all the operations available to manipulate
 * and query the the contained elements, as well as
 * converting back to Java Collection Framework
 * collections.
 *
 *
 * @param <T> The type of the contained objects.
 */
public interface Elements<T> extends
    Mappable<T>, Filterable<T>, Appendable<T>, Prependable<T>,
    YieldsJavaCollection<T>,
    Existance<T>,
    Iterable<T>,
    Reducible<T> {


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


    /**
     * Gets the first element if it exists.
     */
    Optional<T> head();


    /**
     * Skips the first element and gets the remaining elements.
     *
     * @return The remaining elements, or empty elements if there is only one element.
     * @throws NoSuchElementException if there are no elements contained in this <code>Elements</code>,
     *                                i.e. if {@link #head()} returns {@link Singular#none() none}.
     *
     */
    Elements<T> tail();


    /**
     * Join all elements to one <code>String</code>.
     *
     * @return the joined string
     */
    String join();


    /**
     * Join all elements to one <code>String</code> with all
     * elements joined with the given separator.
     *
     * @return the joined string
     */
    String join(String separator);


    @Override public <O> Elements<O> map(Fn<? super T, O> function);
    @Override public Elements<T> filter(Predicate<? super T> filter);

}
