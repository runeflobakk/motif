package no.motif.f.combine;

import no.motif.f.Fn;
import no.motif.f.Fn2;

public class Fn2Chain<I1, I2, T, O> implements Fn2<I1, I2, O> {

    private final Fn2<I1, I2, T> first;
    private final Fn<? super T, O> second;

    public Fn2Chain(Fn2<I1, I2, T> first, Fn<? super T, O> second) {
        this.first = first;
        this.second = second;
    }

    public <N> Fn2Chain<I1, I2, O, N> then(Fn<? super O, N> next) {
        return new Fn2Chain<>(this, next);
    }

    @Override
    public O $(I1 v1, I2 v2) {
        return second.$(first.$(v1, v2));
    }

}
