package no.motif.f.combine;

import no.motif.f.Fn;

/**
 * Composition of two unary functions, where the result of the
 * first is passed as argument to the second.
 * <pre>
 * g(&fnof;(x))
 * </pre>
 *
 *
 * @param <I>
 * @param <T>
 * @param <O>
 */
public class FnChain<I, T, O> implements Fn<I, O> {

    private final Fn<I, T> first;
    private final Fn<? super T, O> second;

    public FnChain(Fn<I, T> first, Fn<? super T, O> second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Append a function to the chain.
     *
     * @param next The next function.
     * @return a new chain with the function appended.
     */
    public <N> FnChain<I, O, N> then(Fn<? super O, N> next) {
        return new FnChain<>(this, next);
    }

    @Override
    public O $(I value) {
        return second.$(first.$(value));
    }

}
