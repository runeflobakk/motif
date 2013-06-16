package no.motif;

import static java.util.Arrays.asList;
import no.motif.iter.PreparedIterable;

public final class Iterate {

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> PreparedIterable<T> on(T... elements) {
        return on(asList(elements));
    }

    public static <T> PreparedIterable<T> on(Iterable<T> elements) {
        return new PreparedIterable<T>(elements);
    }


    private Iterate() {} static { new Iterate(); }

}
