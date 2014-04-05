package no.motif.f;

import static no.motif.Base.always;

/**
 * A <code>PartialApplication</code> is used to build more specialized
 * functions from another function. It is itself a higher order function
 * ({@link Fn Fn&lt;V, FN&gt;}) which takes a value, and yields the
 * specialized function, where the value is passed as the first
 * argument to the more generic function.
 * <p>
 * When doing a partial application of a function with <em>n</em>
 * arguments, a function taking <em>n-1</em> arguments is yielded, where <em>n &gt; 0</em>.
 * Applying the yielded function will complete the partial application.
 * </p>
 *
 * @param <V> Type of the value passed as the first argument
 * @param <FN> The function to partially apply.
 * @param <PARTIALFN> The resulting partially applied function.
 */
public abstract class PartialApplication<FN extends PartialApplicableTo<? super PARTIALFN>, V, PARTIALFN> implements Fn<V, PARTIALFN> {

    private final FN fn;

    PartialApplication(FN partialFn) { this.fn = partialFn; }

    @Override public final PARTIALFN $(V value) { return of(value); }

    public final PARTIALFN of(V value) { return of(always(value)); }

    public final PARTIALFN of(Fn0<V> value) { return partiallyApply(fn, value); }

    /**
     * Do the partial application, i.e. build the arity <em>n-1</em> function.
     *
     * @param fn the function to partially apply
     * @param arg the argument to partially apply <code>fn</code> with.
     * @return the new function resulting from the partial application.
     */
    protected abstract PARTIALFN partiallyApply(FN fn, Fn0<V> arg);

}
