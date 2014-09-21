package no.motif;

import static java.lang.Thread.currentThread;
import static no.motif.Base.always;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import no.motif.f.Fn0;

import org.junit.Test;

public class ImplicitsTest {

    @Test
    public void defaultTimeZone() {
        assertThat(Implicits.getTimeZone(), is(TimeZone.getDefault()));
    }

    @Test
    public void setImplicitTimeZone() {
        TimeZone pst = TimeZone.getTimeZone("PST");
        Implicits.setTimeZone(always(pst));
        assertThat(Implicits.getTimeZone(), is(pst));
        Implicits.setDefaultTimeZone();
    }

    @Test
    public void defaultTimeMillis() {
        long now = System.currentTimeMillis();
        assertThat(Implicits.getTimeMillis(), greaterThanOrEqualTo(now));
    }

    @Test
    public void overrideSystemTime() {
        Implicits.setTimeMillis(new InThisThread<>(always(1000L)).orElse(Implicits.getTimeMillis));
        assertThat(Implicits.getTimeMillis(), is(1000L));
    }

    static class InThisThread<V> implements Fn0<V> {

        private final ThreadLocal<Fn0<V>> threadLocalFn0;

        public InThisThread(Fn0<V> fn0) {
            threadLocalFn0 = new InheritableThreadLocal<Fn0<V>>();
            threadLocalFn0.set(fn0);
        }

        public Fn0<V> orElse(final Fn0<? extends V> fallback) {
            return new Fn0<V>() {
                @Override public V $() {
                    Fn0<V> fn0 = threadLocalFn0.get();
                    if (fn0 == null) return fallback.$();
                    return fn0.$();
                }};
        }


        @Override
        public V $() {
            Fn0<V> fn0 = threadLocalFn0.get();
            if (fn0 == null) throw new IllegalStateException(
                "No Fn0 available for the thread '" + currentThread().getName() + "'");
            return fn0.$();
        }

    }

}
