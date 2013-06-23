package no.motif.iter;

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
public class PreparedIterable<T> implements Iterable<T>, Mappable<T> {

    private final Iterable<T> elements;

    public PreparedIterable(Iterable<T> elements) {
        this.elements = elements;
    }

    public PreparedIterable<T> filter(Predicate<? super T> filter) {
        return new PreparedIterable<>(new FilteredIterable<T>(elements, filter));
    }

    @Override
    public <O> PreparedIterable<O> map(Fn<? super T, O> function) {
        return new PreparedIterable<>(new MappingIterable<T, O>(elements, function));
    }


    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }
}
