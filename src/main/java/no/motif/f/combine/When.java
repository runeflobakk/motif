package no.motif.f.combine;

import static no.motif.Base.always;
import no.motif.f.Fn;
import no.motif.f.Predicate;

public final class When<I, O> implements Fn<I, O> {

    private final Predicate<? super I> condition;
    private final Fn<? super I, ? extends O> fn;
    private final Fn<? super I, ? extends O> orElse;

    public When(Predicate<? super I> condition, Fn<? super I, ? extends O> fn) {
        this(condition, fn, always((O) null));
    }

    private When(Predicate<? super I> condition, Fn<? super I, ? extends O> fn, Fn<? super I, ? extends O> orElse) {
        this.condition = condition;
        this.fn = fn;
        this.orElse = orElse;
    }


    /**
     * @param other The value to return of the condition fails.
     * @return The {@link Fn} for the ternary expression
     *         <pre>condition(v) ? fn(v) : other</pre>
     */
    public Fn<I, O> orElse(O other) {
        return orElse(always(other));
    }

    /**
     * @param otherFn The function to apply if the condition fails.
     * @return The {@link Fn} for the ternary expression
     *         <pre>condition(v) ? fn(v) : otherFn(v)</pre>
     */
    public Fn<I, O> orElse(Fn<? super I, ? extends O> otherFn) {
        return new When<>(condition, fn, otherFn);
    }


    @Override
    public O $(I value) {
        return condition.$(value) ? fn.$(value) : orElse.$(value);
    }

}
