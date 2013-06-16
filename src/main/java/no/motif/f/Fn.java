package no.motif.f;

/**
 * Function taking a value of type <code>I</code> and
 * returning a value of type <code>O</code>.
 *
 * <pre>
 * &fnof;(I) &isin; O
 * </pre>
 *
 * @param <I> the type of the function argument
 * @param <O> The type of object the function returns.
 */
public interface Fn<I, O> {
    O $(I value);
}
