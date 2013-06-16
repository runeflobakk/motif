package no.motif.types;

import no.motif.f.Fn;

/**
 * A type able to map itself to another Mappable
 *
 * @param <T> The type of this Mappable.
 */
public interface Mappable<T> {
    <O> Mappable<O> map(Fn<? super T, O> function);
}
