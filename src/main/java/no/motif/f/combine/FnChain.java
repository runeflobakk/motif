package no.motif.f.combine;

import no.motif.NOP;
import no.motif.f.Do;
import no.motif.f.Fn;

/**
 * Composition of multiple {@link Fn Fns}, where the result of the
 * first is passed as argument to the second.
 * <pre>
 * g(&fnof;(x))
 * </pre>
 */
public abstract class FnChain<I, O> implements Fn<I, O> {

    public static <I, O> FnChain<I, O> chain(Fn<I, O> first) {
        return new Impl<>(first, NOP.<O>fn(), null);
    }


    /**
     * This chain, typed as a {@link Do} (sideeffect).
     */
    @SuppressWarnings("unchecked")
    public final Do<I> asDo = (Do<I>) this;


    /**
     * Append a function to the chain.
     *
     * @param next The next function.
     * @return the resulting new chain with the function appended.
     */
    public abstract <Y> FnChain<I, Y> then(Fn<? super O, Y> next);


    /**
     * Append a {@link Do} (sideeffect) on the output from the last {@link Fn} in the chain.
     * The chain may still be chained with further <code>Fn</code>s or <code>Do</code>s. The chain
     * yields the result from the last <code>Fn</code> as usual, after it has been passed to the given
     * <code>Do</code>. If you need the resulting chain as a <code>Do</code>, use {@link #asDo}.
     *
     * @param sideeffect The {@link Do} to apply on the output from the last <code>Fn</code> in the chain.
     * @return The resulting new chain.
     */
    public abstract FnChain<I, O> then(Do<? super O> sideeffect);


    private FnChain() { }




    private static class Impl<I, T, O> extends FnChain<I, O> implements Do<I> {

        private final Fn<I, T> first;
        private final Fn<? super T, O> second;
        private final Do<? super O> sideeffect;

        private Impl(Fn<I, T> first, Fn<? super T, O> second, Do<? super O> sideeffect) {
            this.first = first;
            this.second = second;
            this.sideeffect = sideeffect;
        }

        @Override
        public <N> FnChain<I, N> then(Fn<? super O, N> next) {
            return new Impl<>(this, next, null);
        }

        @Override
        public FnChain<I, O> then(final Do<? super O> action) {
            return new Impl<>(first, second, action);
        }

        @Override
        public O $(I value) {
            O result = second.$(first.$(value));
            if (sideeffect != null) sideeffect.with(result);
            return result;
        }

        @Override
        public void with(I value) {
            $(value);
        }
    }

}
