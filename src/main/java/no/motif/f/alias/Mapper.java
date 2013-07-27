package no.motif.f.alias;

import no.motif.f.Fn;

/**
 * A <code>Mapper</code> can be implemented
 * in place of an {@link Fn} to communicate
 * that the functions maps values 1:1 to other values.
 * A Mapper does not do anything but read the given argument
 * in order to create another value.
 *
 * This type does not provide anything over {@link Fn} but,
 * where appropriate, being a more specific nominative type,
 * solely for communication purposes.
 *
 * <h3>Good practice</h3>
 * One should never accept
 * <code>Mapper</code>s as argument types, but always
 * implement method signatures to accept {@link Fn}.
 */
public interface Mapper<I, O> extends Fn<I, O> {

}
