package no.motif.f.alias;

import no.motif.f.Fn0;

/**
 * A <code>LazyVal</code> can be implemented
 * in place of an {@link Fn0} to communicate
 * that the value yielded from the function is
 * a lazily calculated.
 * This type does not provide anything over {@link Fn0}
 * but being a more specific nominative type, where
 * appropriate, solely for communication purposes.
 *
 * <h3>Good practice</h3>
 * One should never accept
 * <code>LazyVal</code>s as argument types, but always
 * implement method signatures to accept {@link Fn0}.
 */
public interface LazyVal<V> extends Fn0<V> {}
