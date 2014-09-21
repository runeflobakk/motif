package no.motif;

import static no.motif.Base.always;

import java.util.Locale;
import java.util.TimeZone;

import no.motif.f.Fn0;

/**
 * This class is used to set certain implicit values, which
 * are usually global for larger scopes, e.g. the whole
 * runtime, the context of a web request, etc. Instead of
 * passing these values for each function call, they can be set
 * here as resolvers ({@link Fn0}s), for relevant functions to
 * request their values.
 */
public final class Implicits {

    static final Fn0<Long> systemClockMillis = new Fn0<Long>() {
        @Override public Long $() { return System.currentTimeMillis(); }};


    private static final Fn0<String> defaultEncoding = always("UTF-8");
    private static final Fn0<Locale> defaultLocale = always(Locale.getDefault());
    private static final Fn0<TimeZone> defaultTimeZone = always(TimeZone.getDefault());
    private static final Fn0<Long> defaultTimeMillis = systemClockMillis;

    private static Fn0<String> encoding = defaultEncoding;
    private static Fn0<Locale> locale = defaultLocale;
    private static Fn0<TimeZone> timeZone = defaultTimeZone;
    private static Fn0<Long> timeMillis = defaultTimeMillis;


    public static void setDefaultEncoding() { setEncoding(defaultEncoding); }
    public static void setDefaultLocale() { setLocale(defaultLocale); }
    public static void setDefaultTimeZone() { setTimeZone(defaultTimeZone); }
    public static void useSystemClock() { setTimeMillis(defaultTimeMillis); }


    public static String getEncoding() { return encoding.$(); }
    public static final Fn0<String> getEncoding = new Fn0<String>() { @Override public String $() { return getEncoding(); }};

    public static Locale getLocale() { return locale.$(); }
    public static final Fn0<Locale> getLocale = new Fn0<Locale>() { @Override public Locale $() { return getLocale(); }};

    public static TimeZone getTimeZone() { return timeZone.$(); }
    public static final Fn0<TimeZone> getTimeZone = new Fn0<TimeZone>() { @Override public TimeZone $() { return getTimeZone(); }};

    public static Long getTimeMillis() { return timeMillis.$(); }
    public static final Fn0<Long> getTimeMillis = new Fn0<Long>() { @Override public Long $() { return getTimeMillis(); }};


    public static void setEncoding(Fn0<String> encoding) { synchronized (Implicits.encoding) {Implicits.encoding = encoding;} }

    public static void setLocale(Fn0<Locale> locale) { synchronized (Implicits.locale) {Implicits.locale = locale;} }

    public static void setTimeZone(Fn0<TimeZone> timeZone) { synchronized (Implicits.timeZone) {Implicits.timeZone = timeZone;} }

    public static void setTimeMillis(Fn0<Long> timeMillis) { synchronized (Implicits.timeMillis) {Implicits.timeMillis = timeMillis;} }


    private Implicits() {}

}
