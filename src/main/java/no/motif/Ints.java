package no.motif;

import no.motif.f.Fn2;

public final class Ints {

    public static final Fn2<Number, Number, Integer> sum = new Fn2<Number, Number, Integer>() {
        @Override public Integer $(Number first, Number second) { return first.intValue() + second.intValue(); }};


    public static final Fn2<Number, Number, Integer> multiply = new Fn2<Number, Number, Integer>() {
        @Override public Integer $(Number factor1, Number factor2) { return factor1.intValue() * factor2.intValue(); }};


    private Ints() {} static { new Ints(); }
}
