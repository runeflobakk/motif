package no.motif;

import static no.motif.Base.first;
import no.motif.f.Fn;
import no.motif.f.Fn2;

/**
 * Integer operations. Most of these operations will not fail on numbers to
 * large to fit into a {@link java.lang.Integer}, only truncate as necessary.
 * If operating on larger numbers, consider using the equivalent set of operations
 * from the {@link Longs} class.
 */
public final class Ints {

    /**
     * Yields the {@link Number#intValue() integer value} of any number.
     */
    public static final Fn<Number, Integer> intValue = new Fn<Number, Integer>() {
        @Override public Integer $(Number value) { return value != null ? value.intValue() : 0; }};

    public static final Fn2<Number, Number, Integer> sum = first(Longs.sum).then(intValue);

    public static final Fn2<Number, Number, Integer> multiply = first(Longs.multiply).then(intValue);

    public static final Fn<Number, Integer> doubled = first(Longs.doubled).then(intValue);

    public static final Fn<Number, Integer> rounded = first(Longs.rounded).then(intValue);


    private Ints() {}
}
