package no.motif.f;




/**
 * Function taking two values of type <code>I1</code>
 * and <code>I2</code>, and
 * returning a value of type <code>O</code>.
 *
 * <pre>
 * &fnof;(I1,I2) &isin; O
 * </pre>
 *
 * This function type is often referred to as a "reducer"
 * as it yields one value based on two
 * input values, and it can be applied recursively to reduce
 * an arbitrary amount of elements to one.
 *
 * @param <I1> The first input parameter type.
 * @param <I2> The second input parameter type.
 * @param <O>  The type of object the function returns.
 */
public interface Fn2<I1, I2, O> extends PartialApplicableTo<Fn<I2, O>> {

    /**
     * Applies the function.
     *
     * @param first The first argument to the function application.
     * @param second The second argument to the function application.
     * @return the result from the function application.
     */
    O $(I1 first, I2 second);
}
