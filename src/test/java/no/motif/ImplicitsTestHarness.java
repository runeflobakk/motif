package no.motif;

import java.util.Locale;
import java.util.TimeZone;

import no.motif.f.Fn0;

public final class ImplicitsTestHarness {

    private static final ThreadLocal<String> encoding = new InheritableThreadLocal<>();

    private static final ThreadLocal<Locale> locale = new InheritableThreadLocal<>();

    private static final ThreadLocal<Long> timeMillis = new InheritableThreadLocal<>();

    private static final ThreadLocal<TimeZone> timeZone = new InheritableThreadLocal<>();


    static {
        Implicits.setEncoding(new ThreadLocalWithFallback<>(encoding, Implicits.encoding));
        Implicits.setLocale(new ThreadLocalWithFallback<>(locale, Implicits.locale));
        Implicits.setTimeMillis(new ThreadLocalWithFallback<>(timeMillis, Implicits.timeMillis));
        Implicits.setTimeZone(new ThreadLocalWithFallback<>(timeZone, Implicits.timeZone));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                Implicits.setDefaultEncoding();
                Implicits.setDefaultLocale();
                Implicits.setDefaultTimeZone();
                Implicits.useSystemClock();
            }
        });
    }


    public static void setEncoding(String e) {
        encoding.set(e);
    }

    public static void setLocale(Locale l) {
        locale.set(l);
    }

    public static void setTimeZone(TimeZone tz) {
        timeZone.set(tz);
    }

    public static void setTimeMillis(Long ms) {
        timeMillis.set(ms);
    }


    private static final class ThreadLocalWithFallback<T> implements Fn0<T> {

        private final ThreadLocal<T> threadLocal;
        private final Fn0<T> fallback;

        public ThreadLocalWithFallback(ThreadLocal<T> threadLocal, Fn0<T> fallback) {
            this.threadLocal = threadLocal;
            this.fallback = fallback;
        }

        @Override
        public T $() {
            T threadLocalValue = threadLocal.get();
            return threadLocalValue != null ? threadLocalValue : fallback.$();
        }
    }

}
