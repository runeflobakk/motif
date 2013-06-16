package no.motif;

import no.motif.f.Fn;

public final class Functions {

    public static final Fn<Object, String> toString =
            new Fn<Object, String>() { @Override public String $(Object value) { return value.toString(); }};

    private Functions() {} static { new Functions(); }
}
