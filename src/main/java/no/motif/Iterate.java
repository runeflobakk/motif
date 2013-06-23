package no.motif;

import static java.util.Arrays.asList;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import no.motif.f.Fn;
import no.motif.f.Predicate;
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

    public static final Predicate<Iterator<?>> hasNext = new Predicate<Iterator<?>>() {
        @Override public boolean $(Iterator<?> iterator) { return iterator.hasNext(); }};

    public static final <T> Fn<Iterator<T>, T> next() {
        return new Fn<Iterator<T>, T>() {
            @Override
            public T $(Iterator<T> iterator) {
                return iterator.next();
            }
        };
    };

    private Iterate() {} static { new Iterate(); }

}
