package no.motif.f;


/**
 * Function taking a value of type <code>I</code> and
 * returning a value of type <code>O</code>.
 *
 * <pre>
 * &fnof;(I) &isin; O
 * </pre>
 *
 * <p>This function type is often referred to as a "mapper"
 * or "transformer", as it takes one value and based on it,
 * yields another value. The function defines the mapping
 * between values.</p>
 *
 * <p>This function type is the most commonly used one, and is
 * promoted to be named without an index, whereas the other
 * function types are named as <code>FnX</code>, where
 * <code>X</code> denotes how many arguments it takes. I.e.
 * {@link Fn0}, {@link Fn2}, etc.
 *
 *
 * @param <I> the type of the function argument
 * @param <O> The type of object the function returns.
 */
public interface Fn<I, O> extends PartialApplicableTo<Fn0<O>> {

    /**
     * Applies the function.
     *
     * @param value The value to apply the function on.
     * @return the result from the function application.
     */
    O $(I value);

}
