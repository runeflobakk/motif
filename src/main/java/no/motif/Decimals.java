package no.motif;

import no.motif.f.Fn2;

public final class Decimals {

    public static final Fn2<Number, Number, Double> pow = new Fn2<Number, Number, Double>() {
        @Override public Double $(Number base, Number exponent) {
        	return java.lang.Math.pow(base.doubleValue(), exponent.doubleValue()); }};

    private Decimals() {}
}
