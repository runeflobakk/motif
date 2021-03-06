package no.motif.types;

import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.single.Elem;
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
    Mappable<T>, Filterable<T>, Appendable<T>, Prependable<T>, SideEffectable<T>, Repeatable<T>,
    HeadAndTail<T>,
    YieldsJavaCollection<T>,
    Existance<T>,
    Iterable<T>,
    Reducible<T> {

    /**
     * Couple each element with its positional index.
     *
     * @return The elements wrapped in {@link Elem}s.
     */
    Elements<Elem<T>> indexed();


    /**
     * Couple each element with its positional index,
     * starting from a given index.
     *
     * @param startIndex the beginning index to assign to the first element.
     * @return The elements wrapped in {@link Elem}s.
     */
    Elements<Elem<T>> indexedFrom(int startIndex);


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
     * Gets the last element.
     */
    Optional<T> last();

    @Override
    Optional<T> head();

    @Override
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


    @Override <O> Elements<O> map(Fn<? super T, O> function);
    @Override Elements<T> filter(Predicate<? super T> filter);
    @Override Elements<T> repeat(int times);

    <O> Elements<O> flatMap(Fn<? super T, ? extends Iterable<O>> fn);

    /**
     * Force-evaluate the current elements, applying all composed functions
     * on each element. This is useful when needing to iterate elements
     * several times, especially if the composed functions are expensive to evaluate
     * and/or not referentially transparent.
     *
     * This is an escape-hatch from the lazy design of {@link Elements}.
     *
     * @return same type of elements, with all elements evaluated.
     */
    Elements<T> eval();


}
