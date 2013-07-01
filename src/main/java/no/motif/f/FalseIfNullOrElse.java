package no.motif.f;

/**
 * A base implementation for predicates where the case of <code>null</code>
 * values is evaluated as false. For every other non-null value, sub-classes
 * must implement the {@link #$nullsafe(Object)} method to decide if
 * the value should evaluate to <code>true</code> or <code>false</code>,
 * but need not to worry about checking if the value is <code>null</code>.
 */
public abstract class FalseIfNullOrElse<T> implements Predicate<T> {

    /**
     * @param value The value to evaluate, never <code>null</code>.
     * @return <code>true</code> or <code>false</code>
     */
    protected abstract boolean $nullsafe(T value);

    @Override
    public boolean $(T value) {
        return value != null && $nullsafe(value);
    }

}
