package no.motif.types;

import no.motif.f.Fn;
import no.motif.f.Predicate;

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


    @Override public <O> Elements<O> map(Fn<? super T, O> function);
    @Override public Elements<T> filter(Predicate<? super T> filter);

}
