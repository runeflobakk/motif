package no.motif;

import static no.motif.Base.not;
import static no.motif.Base.notNull;
import static no.motif.Base.when;
import static no.motif.f.Apply.argsReversed;
import no.motif.f.Apply;
import no.motif.f.Fn;
import no.motif.f.Fn2;
import no.motif.f.Predicate;
import no.motif.f.base.FalseIfNull;
import no.motif.f.base.NullIfEitherArgIsNull;

public final class Longs {

    public static final Predicate<Long> even = new FalseIfNull<Long>() {
        @Override public boolean orElse(Long i) { return i % 2 == 0; }};

    public static final Predicate<Long> odd = not(even);


    /**
     * Yields the {@link Number#longValue() long value} of any number.
     */
    public static final Fn<Number, Long> longValue = when(notNull, new Fn<Number, Long>() {
        @Override public Long $(Number value) { return value.longValue(); }});


    public static final Fn2<Number, Number, Long> sum = new NullIfEitherArgIsNull<Number, Number, Long>() {
        @Override public Long orElse(Number first, Number second) {
            return first.longValue() + second.longValue(); }};


    public static final Fn2<Number, Number, Long> multiply = new NullIfEitherArgIsNull<Number, Number, Long>() {
        @Override public Long orElse(Number factor1, Number factor2) {
            return factor1.longValue() * factor2.longValue(); }};


    public static final Fn2<Number, Number, Long> divide = new Fn2<Number, Number, Long>() {
        @Override public Long $(Number factor1, Number factor2) {
            return factor1.longValue() / factor2.longValue(); }};


    public static final Fn<Number, Long> doubled = multipliedBy(2);


    public static final Fn<Number, Long> rounded = when(notNull, new Fn<Number, Long>() {
        @Override public Long $(Number decimal) { return Math.round(decimal.doubleValue()); }});


    public static final Fn<Number, Long> increment = when(notNull, new Fn<Number, Long>() {
        @Override public Long $(Number n) { return n.longValue() + 1; }});

    public static final Fn<Number, Long> add(long value) { return when(notNull, Apply.partially(sum).of(value)); }

    public static final Fn<Number, Long> subtract(long value) { return add(value * -1); }

    public static final Fn<Number, Long> multipliedBy(long value) { return when(notNull, Apply.partially(multiply).of(value)); }

    public static final Fn<Number, Long> dividedBy(long value) { return when(notNull, Apply.partially(argsReversed(divide)).of(value)); }


    private Longs() {}

}
