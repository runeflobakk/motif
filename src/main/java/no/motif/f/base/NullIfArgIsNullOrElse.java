package no.motif.f.base;

import no.motif.f.Fn;

/**
 * A convenient base implementation of {@link Fn} which implements
 * no operation (NOP) for <code>null</code> values. For any non-null argument,
 * the argument is passed to {@link #$nullsafe(Object)}, which
 * can be implemented without any regard to possible null-pointers.
 *
 *
 * @see Fn
 */
public abstract class NullIfArgIsNullOrElse<I, O> extends DefaultIfArgIsNullOrElse<I, O> {

    protected NullIfArgIsNullOrElse() { super(null); }

}
