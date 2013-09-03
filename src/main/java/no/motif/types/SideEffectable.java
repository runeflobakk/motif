package no.motif.types;

import no.motif.f.Do;

/**
 * A container which can pass its element(s) to
 * a {@link Do}s, in order to compute side effects.
 *
 * @param <T> The type of the contained elements.
 */
public interface SideEffectable<T> {

    /**
     * Execute a side effect operation for each
     * containing element.
     *
     * @param sideEffect the {@link Do} operation which is
     *                   passed each of the containing elements.
     */
    void each(Do<? super T> sideEffect);

}
