package no.motif.f;

/**
 * Utilities to adapt how functions are invoked.
 *
 * <p><strong>Note:</strong>
 * This is part of the more advanced API, and might seem
 * a bit intimidating to some. It is however <em>not</em>
 * a requirement to be acquainted with this API and still
 * benefit from using the Motif library. This part of the API
 * should be considered as a small "value-add" for developers who
 * are comfortable with these concepts.</p>
 */
public final class Apply {


    /**
     * {@link PartialApplication Partially apply} a {@link Fn2 function taking 2 arguments}.
     * @see    PartialApplication
     *
     * @param  fn2 The function to partially apply.
     * @return use {@link PartialApplication#of(Object) of()}
     *         to partially invoke <code>fn2</code> and yield an {@link Fn}
     */
    public static final <I1, I2, O> PartialApplication<Fn2<I1, I2, O>, I1, Fn<I2, O>> partially(Fn2<I1, I2, O> fn2) { return new PartialApplication<Fn2<I1, I2, O>, I1, Fn<I2,O>>(fn2) {
        @Override public Fn<I2, O> partiallyApply(final Fn2<I1, I2, O> fn2, final Fn0<I1> v1) { return new Fn<I2, O>() {
            @Override public O $(I2 v2) { return fn2.$(v1.$(), v2); }}; }}; }


    /**
     * {@link PartialApplication Partially apply} a {@link Fn function taking 1 argument}.
     *
     * @param fn The function to partially apply.
     * @return use {@link PartialApplication#of(Object) of()}
     *         to partially invoke <code>fn</code> and yield an {@link Fn0}
     */
    public static final <I, O> PartialApplication<Fn<I, O>, I, Fn0<O>> partially(Fn<I, O> fn) { return new PartialApplication<Fn<I, O>, I, Fn0<O>>(fn) {
        @Override public Fn0<O> partiallyApply(final Fn<I, O> fn, final Fn0<I> v1) { return new Fn0<O>() {
            @Override public O $() { return fn.$(v1.$()); }}; }}; }


    private Apply() {}

}
