package no.motif.f.base;

import no.motif.f.Fn;
import no.motif.f.Fn0;
import no.motif.f.Fn2;

public class Constant<V, I1, I2> implements Fn0<V>, Fn<I1, V>, Fn2<I1, I2, V> {

    private V value;

    public Constant(V value) {
        this.value = value;
    }

    @Override
    public V $() {
        return value;
    }

    @Override
    public V $(Object discarded) {
        return value;
    }

    @Override
    public V $(Object firstDiscarded, Object secondDiscarded) {
        return value;
    }



}
