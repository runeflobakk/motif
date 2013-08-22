package no.motif.iter;

import static java.util.Collections.emptyList;
import static no.motif.Base.not;
import static no.motif.Singular.optional;

import java.util.Iterator;

import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.types.Elements;

/**
 * This class wraps an arbitrary {@link Iterable} and provides access to
 * various operations to view and/or transform the wrapped iterable.
 *
 * @param <T> The type of the elements in the iterable.
 */
public class PreparedIterable<T> extends CollectingIterable<T> implements Iterable<T>, Elements<T> {

    private static final PreparedIterable<?> EMPTY = new PreparedIterable<>(emptyList());

    @SuppressWarnings("unchecked")
    public static <T> PreparedIterable<T> empty() {
        return (PreparedIterable<T>) EMPTY;
    }

    private final Iterable<T> elements;

    public PreparedIterable(Iterable<T> elements) {
        this.elements = elements;
    }


    @Override
    public PreparedIterable<T> filter(Predicate<? super T> filter) {
        return new PreparedIterable<>(new FilteredIterable<>(elements, filter));
    }


    @Override
    public <O> PreparedIterable<O> map(Fn<? super T, O> function) {
        return new PreparedIterable<>(new MappingIterable<>(elements, function));
    }


    @Override
    public PreparedIterable<T> append(T value) {
        return append(optional(value));
    }


    @Override
    public PreparedIterable<T> append(Iterable<? extends T> trailingElements) {
        return new PreparedIterable<>(new ConcatenatedIterable<>(elements, trailingElements));
    }


    @Override
    public PreparedIterable<T> prepend(T value) {
        return prepend(optional(value));
    }


    @Override
    public PreparedIterable<T> prepend(Iterable<? extends T> leadingElements) {
        return new PreparedIterable<>(new ConcatenatedIterable<>(leadingElements, elements));
    }


    @Override
    public PreparedIterable<T> take(int amount) {
        return new PreparedIterable<>(new BoundedIterable<>(amount, elements));
    }


    @Override
    public PreparedIterable<T> takeWhile(Predicate<? super T> predicate) {
        return new PreparedIterable<>(new PredicateBoundedIterable<>(predicate, elements));
    }


    @Override
    public PreparedIterable<T> takeUntil(Predicate<? super T> predicate) {
        return new PreparedIterable<>(new PredicateBoundedIterable<>(not(predicate), elements));
    }


    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }

}
