package no.motif.f.base;

import no.motif.f.Fn2;

/**
 * A convenient base implementation of {@link Fn2} which yields
 * <code>null</code> if one or both arguments is <code>null</code>.
 * When both arguments are non-null, they are passed to
 * {@link #orElse(Object, Object)}, which
 * can be implemented without any regard to possible null-pointers.
 *
 * @see Fn2
 */
public abstract class NullIfEitherArgIsNull<I1, I2, O> implements Fn2<I1, I2, O> {

    @Override
    public O $(I1 first, I2 second) {
        return (first == null || second == null) ? null : orElse(first, second);
    }

    /**
     * The function implementation.
     * @param first the first function argument, guarantied not to be <code>null</code>.
     * @param second the second function argument, guarantied not to be <code>null</code>.
     * @return the function result.
     */
    protected abstract O orElse(I1 first, I2 second);

}
