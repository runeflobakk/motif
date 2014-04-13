package no.motif;

import no.motif.f.Do;
import no.motif.f.Fn;


/**
 * A few no-operation implementations.
 */
public final class NOP {


    /**
     * A {@link Runnable} which does nothing and its {@link Runnable#run() run}-method
     * immediately returns.
     */
    public static final Runnable runnable = new Runnable() { @Override public void run() { }};

    /**
     * The NOP for {@link Fn} simply yields any given argument as its result.
     */
    @SuppressWarnings("unchecked")
    public static <V> Fn<V, V> fn() { return (Fn<V, V>) fn; }

    /**
     * The NOP for {@link Do} does nothing.
     */
    public static final Do<Object> doNothing = new Do<Object>() { @Override public void with(Object value) { }};


    @SuppressWarnings("rawtypes")
    private static final Fn<?, ?> fn = new Fn() { @Override public Object $(Object value) { return value; }};


    private NOP() {}
}
