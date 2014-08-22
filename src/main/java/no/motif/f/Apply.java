package no.motif.f;

/**
 * Utilities to adapt how functions are invoked.
 */
public final class Apply {


    /**
     * Convert an {@link Fn} to a {@link Do}. This is only usable for <code>Fns</code> which
     * performs some kind of side effect, and you need to pass it somewhere which expects a
     * <code>Do</code>.
     *
     * @param fn The {@link Fn} to apply as a <code>Do</code>.
     * @return The resulting {@link Do}
     */
    public static final <I> Do<I> justSideEffectOf(final Fn<I, ?> fn) {
        return new Do<I>() { @Override public void with(I value) { fn.$(value); }}; }


    /**
     * Transform a function taking 2 arguments to another function where
     * the argument order is reversed.
     *
     * @param fn2 the function
     * @return a new {@link Fn2} where the argument order is reversed.
     */
    public static final <I1, I2, O> Fn2<I2, I1, O> argsReversed(final Fn2<I1, I2, O> fn2) {
        return new Fn2<I2, I1, O>() { @Override public O $(I2 second, I1 first) { return fn2.$(first, second); }}; }


    /**
     * {@link PartialApplication Partially apply} a {@link Fn2 function taking 2 arguments}.
     *
     *
     * @param  fn2 The function to partially apply.
     * @return use {@link PartialApplication#of(Object) of()}
     *         to partially invoke <code>fn2</code> and yield an {@link Fn}
     *
     * @see    PartialApplication
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
     *
     * @see    PartialApplication
     */
    public static final <I, O> PartialApplication<Fn<I, O>, I, Fn0<O>> partially(Fn<I, O> fn) { return new PartialApplication<Fn<I, O>, I, Fn0<O>>(fn) {
        @Override public Fn0<O> partiallyApply(final Fn<I, O> fn, final Fn0<I> v1) { return new Fn0<O>() {
            @Override public O $() { return fn.$(v1.$()); }}; }}; }





    private Apply() {}

}
