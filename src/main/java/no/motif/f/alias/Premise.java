package no.motif.f.alias;

import no.motif.f.Fn0;
import no.motif.f.Predicate;

/**
 * A <code>Premise</code> is the same as a
 * {@link Fn0 Fn0&lt;Boolean&gt;}, e.g. a <code>true</code>/<code>false</code>
 * value, or fact, that may or may not change over time. It differs
 * from a {@link Predicate} in that it depends on the environment
 * instead of calculating its result based on an input argument.
 * <p>
 * Since the result of a <code>Premise</code> is a {@link java.lang.Boolean}, it is
 * possible assign semantics to a returned <code>null</code> value (e.g. "undecided" or similar),
 * but you <em>must</em> make
 * sure that code using such <code>Fn0&lt;Boolean&gt;s</code> that may return <code>null</code>
 * are in fact handling <code>null</code>s appropriately. In general, a <code>Premise</code> or
 * <code>Fn0&lt;Boolean&gt;</code> should <em>never</em> return <code>null</code>.
 * </p>
 * <p>
 * This type does not provide anything over {@link Fn0 Fn0&lt;Boolean&gt;}
 * but being a more specific nominative type, where
 * appropriate, solely for communication purposes.
 * </p>
 *
 * <h3>Good practice</h3>
 * One should never accept
 * <code>Premise</code>s as argument types, but always
 * implement method signatures to accept {@link Fn0 Fn0&lt;Boolean&gt;}.
 */
public interface Premise extends Fn0<Boolean> {}
