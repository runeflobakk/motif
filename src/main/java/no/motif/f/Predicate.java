package no.motif.f;

import java.io.Serializable;

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
public interface Predicate<T> extends Serializable {
    boolean $(T value);

    final class Always<T> implements Predicate<T> {

        private static final long serialVersionUID = 1L;

        @SuppressWarnings("unchecked")
        public static <T> Predicate<T> no() { return (Predicate<T>) FALSE; }

        @SuppressWarnings("unchecked")
        public static <T> Predicate<T> yes() { return (Predicate<T>) TRUE; }


        private static final Predicate<?> FALSE = new Always<Object>(false);
        private static final Predicate<?> TRUE = new Always<Object>(true);

        private final boolean constant;

        private Always(final boolean constant) {
            this.constant = constant;
        }

        @Override
        public final boolean $(T value) {
            return constant;
        }
    }
}
