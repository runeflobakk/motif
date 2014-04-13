package no.motif.f.base;

import static no.motif.Exceptions.asRuntimeException;
import no.motif.f.Do;
import no.motif.f.Fn;
import no.motif.f.Fn0;
import no.motif.f.Fn2;

public class Throw<I1, I2, O> implements Fn0<O>, Fn<I1, O>, Fn2<I1, I2, O>, Do<I1> {

    private final Exception e;

    public Throw(Exception e) { this.e = e; }

    @Override public O $(I1 first, I2 second) { throw asRuntimeException(e); }

    @Override public O $() { throw asRuntimeException(e); }

    @Override public O $(I1 value) { throw asRuntimeException(e); }

    @Override public void with(I1 value) { throw asRuntimeException(e); }

}
