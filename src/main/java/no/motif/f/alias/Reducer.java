package no.motif.f.alias;

import no.motif.f.Fn2;

/**
 * A <code>Reducer</code> can be implemented
 * in place of an {@link Fn2} to communicate
 * that the functions reduces two values to one.
 *
 * This type does not provide anything over {@link Fn2} but,
 * where appropriate, being a more specific nominative type,
 * solely for communication purposes.
 *
 * <h3>Good practice</h3>
 * One should never accept
 * <code>Reducer</code>s as argument types, but always
 * implement method signatures to accept {@link Fn2}.
 */
public interface Reducer<I1, I2, O> extends Fn2<I1, I2, O> {

}
