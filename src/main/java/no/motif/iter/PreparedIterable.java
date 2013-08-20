package no.motif.iter;

import static java.util.Collections.emptyList;
import static no.motif.Base.not;
import static no.motif.Singular.optional;

import java.util.Iterator;

import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.types.Mappable;

/**
 * This class wraps an arbitrary {@link Iterable} and provides access to
 * various operations to view and/or transform the wrapped iterable.
 *
 * @param <T> The type of the elements in the iterable.
 */
public class PreparedIterable<T> extends CollectingIterable<T> implements Iterable<T>, Mappable<T> {

    private static final PreparedIterable<?> EMPTY = new PreparedIterable<>(emptyList());

    @SuppressWarnings("unchecked")
    public static <T> PreparedIterable<T> empty() {
        return (PreparedIterable<T>) EMPTY;
    }

    private final Iterable<T> elements;

    public PreparedIterable(Iterable<T> elements) {
        this.elements = elements;
    }

    public PreparedIterable<T> filter(Predicate<? super T> filter) {
        return new PreparedIterable<>(new FilteredIterable<>(elements, filter));
    }

    @Override
    public <O> PreparedIterable<O> map(Fn<? super T, O> function) {
        return new PreparedIterable<>(new MappingIterable<>(elements, function));
    }

    public PreparedIterable<T> append(T value) {
        return append(optional(value));
    }

    public PreparedIterable<T> append(Iterable<? extends T> trailingElements) {
        return new PreparedIterable<>(new ConcatenatedIterable<>(elements, trailingElements));
    }

    public PreparedIterable<T> take(int amount) {
        return new PreparedIterable<>(new BoundedIterable<>(amount, elements));
    }

    public PreparedIterable<T> takeWhile(Predicate<? super T> predicate) {
        return new PreparedIterable<>(new TakeWhile<>(predicate, elements));
    }

    public PreparedIterable<T> takeUntil(Predicate<? super T> predicate) {
        return new PreparedIterable<>(new TakeWhile<>(not(predicate), elements));
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

}
