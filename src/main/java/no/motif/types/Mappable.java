package no.motif.types;

import no.motif.f.Fn;

/**
 * A type able to map itself to another Mappable
 *
 * @param <T> The type of value this Mappable contains.
 */
public interface Mappable<T> {

    /**
     * Apply a function on any contained element(s), returning the result
     * in a new mappable container.
     *
     * @param function The function to apply.
     * @return The result from the function application.
     */
    <O> Mappable<O> map(Fn<? super T, O> function);
}
