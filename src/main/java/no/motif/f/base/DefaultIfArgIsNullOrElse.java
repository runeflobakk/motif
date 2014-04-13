package no.motif.f.base;

import no.motif.f.Fn;

/**
 * A convenient base implementation of {@link Fn} which yields a default
 * value for passed <code>null</code> values. For any non-null argument,
 * the argument is passed to {@link #$nullsafe(Object)}, which
 * can be implemented without any regard to possible null-pointers.
 *
 *
 * @see Fn
 */
public abstract class DefaultIfArgIsNullOrElse<I, O> implements Fn<I, O> {

    private O defaultValue;

    protected DefaultIfArgIsNullOrElse(O defaultValue) { this.defaultValue = defaultValue; }

    @Override
    public final O $(I value) { return value != null ? $nullsafe(value) : defaultValue; }


    /**
     * The function implementation.
     *
     * @param value the function argument, guarantied not to be <code>null</code>.
     * @return the function result.
     */
    protected abstract O $nullsafe(I value);
}
