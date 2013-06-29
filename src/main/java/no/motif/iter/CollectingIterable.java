package no.motif.iter;

import no.motif.f.Fn2;

/**
 * This iterable offers operations which requires collecting (i.e.
 * iterating) the containing elements of the iterable.
 *
 * @param <T> The type of elements in this iterable.
 */
public abstract class CollectingIterable<T> implements Iterable<T> {

    /**
     * Reduce the elements into one value.
     *
     * @param unit The start value for the reduction. The unit should in general (but
     *             is not required to) be such that when combined with any element in
     *             the iterable, yield the iterable's element.
     *
     * @param reducer the function which first argument is the accumulated reduction
     *                througout the iteration, and second argument the value to include
     *                in the reduction.
     *
     * @return The resulting value from the reduction.
     */
    public <O> O reduce(O unit, Fn2<? super O, ? super T, ? extends O> reducer) {
        O reduced = unit;
        for (T element : this) reduced = reducer.$(reduced, element);
        return reduced;
    }

}
