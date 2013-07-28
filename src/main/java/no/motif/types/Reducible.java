package no.motif.types;

import no.motif.f.Fn2;

/**
 * A type, usually a container of multiple values, which is reducible
 * to some single value.
 *
 * @param <T> The type contained in the object.
 */
public interface Reducible<T> {

    /**
     * Reduce the element(s) into one value.
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
    <V> V reduce(V unit, Fn2<? super V, ? super T, ? extends V> reducer);
}
