package no.motif.f.base;

import no.motif.f.Predicate;

/**
 * A base implementation for predicates where the case of <code>null</code>
 * values is evaluated as false. For every other non-null value, sub-classes
 * must implement the {@link #orElse(Object)} method to decide if
 * the value should evaluate to <code>true</code> or <code>false</code>,
 * but need not to worry about checking if the value is <code>null</code>.
 */
public abstract class FalseIfNull<T> implements Predicate<T> {

    /**
     * @param value The value to evaluate, never <code>null</code>.
     * @return <code>true</code> or <code>false</code>
     */
    protected abstract boolean orElse(T value);

    @Override
    public final boolean $(T value) { return value != null && orElse(value); }

}
