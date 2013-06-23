package no.motif;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Set;

import no.motif.iter.PreparedIterable;


/**
 * Operations for iterables, e.g.
 * {@link List lists}, {@link Set sets}, or any implementation
 * of {@link Iterable}.
 *
 * @see PreparedIterable
 */
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
