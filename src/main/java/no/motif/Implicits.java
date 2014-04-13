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

    private static Fn0<Locale> locale;
    private static Fn0<String> encoding;
    private static Fn0<TimeZone> timeZone;

    static { setDefaultEncoding(); setDefaultLocale(); setDefaultTimeZone(); }

    public static void setDefaultEncoding() { encoding = always("UTF-8"); }
    public static void setDefaultLocale() { locale = always(Locale.getDefault()); }
    public static void setDefaultTimeZone() { timeZone = always(TimeZone.getDefault()); }


    public static String getEncoding() { return encoding.$(); }

    public static Locale getLocale() { return locale.$(); }

    public static TimeZone getTimeZone() { return timeZone.$(); }


    public static void setEncoding(Fn0<String> encoding) { Implicits.encoding = encoding; }

    public static void setLocale(Fn0<Locale> locale) { Implicits.locale = locale; }

    public static void setTimeZone(Fn0<TimeZone> timeZone) { Implicits.timeZone = timeZone; }

    private Implicits() {}

}
