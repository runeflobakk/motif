package no.motif;

import static no.motif.f.Apply.argsReversed;
import no.motif.f.Apply;
import no.motif.f.Fn;
import no.motif.f.Fn2;

public final class Longs {

    public static final Fn<Number, Long> longValue = new Fn<Number, Long>() {
        @Override public Long $(Number value) { return value != null ? value.longValue() : 0; }};


    public static final Fn2<Number, Number, Long> sum = new Fn2<Number, Number, Long>() {
        @Override public Long $(Number first, Number second) {
            return first.longValue() + second.longValue(); }};


    public static final Fn2<Number, Number, Long> multiply = new Fn2<Number, Number, Long>() {
        @Override public Long $(Number factor1, Number factor2) {
            return factor1.longValue() * factor2.longValue(); }};

    public static final Fn2<Number, Number, Long> divide = new Fn2<Number, Number, Long>() {
        @Override public Long $(Number factor1, Number factor2) {
            return factor1.longValue() / factor2.longValue(); }};


    public static final Fn<Number, Long> doubled = Apply.partially(multiply).of(2);


    public static final Fn<Number, Long> rounded = new Fn<Number, Long>() {
        @Override public Long $(Number decimal) { return Math.round(decimal.doubleValue()); }};


    public static final Fn<Number, Long> increment = new Fn<Number, Long>() {
        @Override public Long $(Number n) { return n.longValue() + 1; }};

    public static final Fn<Number, Long> add(long value) { return Apply.partially(sum).of(value); }

    public static final Fn<Number, Long> subtract(long value) { return add(value * -1); }

    public static final Fn<Number, Long> multipliedBy(long value) { return Apply.partially(multiply).of(value); }

    public static final Fn<Number, Long> dividedBy(long value) { return Apply.partially(argsReversed(divide)).of(value); }


    private Longs() {}

}
