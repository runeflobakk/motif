package no.motif.f.combine;

import no.motif.f.Fn;
import no.motif.f.Predicate;

public final class Where<T, P> implements Predicate<T> {
    private final Fn<T, P> fn;
    private final Predicate<? super P> predicate;

    public Where(Fn<T, P> fn, Predicate<? super P> predicate) {
        this.fn = fn;
        this.predicate = predicate;
    }

    @Override public boolean $(T value) { return predicate.$(fn.$(value)); }
}