package no.motif.f;

/**
 * A predicate is a function taking a value of type <code>T</code>, and evaluates it
 * as either <code>true</code> or <code>false</code>.
 *
 * <pre>
 * &fnof;(T) &isin; {true, false}
 * </pre>
 *
 * Semantically, a predicate is a specialization
 * of {@link Fn}, with the return type set to primitive {@link Boolean boolean}.
 * Since Java does not allow type parameterization with primitive types, and predicates
 * play such an important role, it has been modeled as its own separate type.
 *
 * @param <T> The type of objects the predicate can evaluate.
 */
public interface Predicate<T> {
    boolean $(T value);
}
