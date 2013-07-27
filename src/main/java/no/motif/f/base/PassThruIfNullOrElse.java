package no.motif.f.base;

import no.motif.f.Fn;

/**
 * A convenient base implementation of {@link Fn} which implements
 * no operation (NOP) for <code>null</code> values. For any non-null,
 * the function value is passed to {@link #$nullsafe(Object)}, which
 * can be implemented without any regard to possible null-pointers.
 * When the function is passed a <code>null</code> value, it returns
 * </code>null</code>, thus adhering to a simple
 * "garbage&nbsp;in&nbsp;&dash;&gt;&nbsp;garbage&nbsp;out" policy.
 *
 *
 * @see Fn
 */
public abstract class PassThruIfNullOrElse<I, O> implements Fn<I, O> {

    @Override
    public O $(I value) { return value != null ? $nullsafe(value) : null; }

    /**
     * The function implementation.
     * @param value the function argument, guarantied not to be <code>null</code>.
     * @return the function result.
     */
    protected abstract O $nullsafe(I value);

}
