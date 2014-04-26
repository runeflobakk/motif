package no.motif;

import static no.motif.Base.first;
import static no.motif.Base.not;
import static no.motif.Base.notNull;
import static no.motif.Base.when;
import static no.motif.f.Apply.argsReversed;
import no.motif.f.Apply;
import no.motif.f.Fn;
import no.motif.f.Fn2;
import no.motif.f.Predicate;
import no.motif.f.base.FalseIfNull;

/**
 * Integer operations. Most of these operations will not fail on numbers to
 * large to fit into a {@link java.lang.Integer}, only truncate as necessary.
 * If operating on larger numbers, consider using the equivalent set of operations
 * from the {@link Longs} class.
 */
public final class Ints {

    public static final Predicate<Integer> even = new FalseIfNull<Integer>() {
        @Override public boolean orElse(Integer i) { return i % 2 == 0; }};

    public static final Predicate<Integer> odd = not(even);

    /**
     * Yields the {@link Number#intValue() integer value} of any number.
     */
    public static final Fn<Number, Integer> intValue = when(notNull, new Fn<Number, Integer>() {
        @Override public Integer $(Number value) { return value.intValue(); }});

    public static final Fn2<Number, Number, Integer> sum = first(Longs.sum).then(intValue);

    public static final Fn2<Number, Number, Integer> multiply = first(Longs.multiply).then(intValue);

    public static final Fn2<Number, Number, Integer> divide = first(Longs.divide).then(intValue);

    public static final Fn<Number, Integer> doubled = first(Longs.doubled).then(intValue);

    public static final Fn<Number, Integer> rounded = first(Longs.rounded).then(intValue);

    public static final Fn<Number, Integer> increment = first(Longs.increment).then(intValue);

    public static final Fn<Number, Integer> add(int value) { return when(notNull, Apply.partially(sum).of(value)); }

    public static final Fn<Number, Integer> subtract(int value) { return add(value * -1); }

    public static final Fn<Number, Integer> multipliedBy(int value) { return when(notNull, Apply.partially(multiply).of(value)); }

    public static final Fn<Number, Integer> dividedBy(int value) { return when(notNull, Apply.partially(argsReversed(divide)).of(value)); }


    private Ints() {}
}
