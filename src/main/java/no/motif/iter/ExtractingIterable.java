package no.motif.iter;

import static no.motif.Singular.none;
import static no.motif.Singular.optional;

import java.util.Iterator;

import no.motif.f.Fn;
import no.motif.single.Optional;

/**
 * An iterable which will derive its elements from one object, by using
 * a set of {@link Fn}s which each will be given said object to obtain
 * the elements.
 *
 */
public class ExtractingIterable<T, E> implements Iterable<E> {

    private final T value;
    private final Iterable<? extends Fn<? super T, ? extends E>> extractors;

    public ExtractingIterable(T value, Iterable<? extends Fn<? super T, ? extends E>> extractors) {
        this.value = value;
        this.extractors = extractors;
    }

    @Override
    public Iterator<E> iterator() {
        return new SimpleIterator<E>() {
            final Iterator<? extends Fn<? super T, ? extends E>> iterator = extractors.iterator();

            @Override
            protected Optional<? extends E> nextIfAvailable() {
                if (!iterator.hasNext()) return none();
                else return optional(iterator.next().$(value));
            }};
    }

}
