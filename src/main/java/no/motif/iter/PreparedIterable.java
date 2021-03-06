package no.motif.iter;

import static no.motif.Base.not;
import static no.motif.Iterate.none;
import static no.motif.Iterate.on;
import static no.motif.Singular.optional;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.single.Elem;
import no.motif.types.Elements;

/**
 * This class wraps an arbitrary {@link Iterable} and provides access to
 * various operations to view and/or transform the wrapped iterable.
 *
 * @param <T> The type of the elements in the iterable.
 */
public class PreparedIterable<T> extends CollectingIterable<T> implements Elements<T>, Serializable {

    private final Iterable<T> elements;

    public PreparedIterable(Iterable<T> elements) {
        this.elements = elements;
    }


    @Override
    public Elements<Elem<T>> indexed() {
        return indexedFrom(0);
    }

    @Override
    public Elements<Elem<T>> indexedFrom(int startIndex) {
        return new PreparedIterable<>(new IndexedIterable<>(startIndex, elements));
    }


    @Override
    public PreparedIterable<T> filter(Predicate<? super T> filter) {
        return new PreparedIterable<>(new FilteredIterable<>(elements, filter));
    }


    @Override
    public <O> PreparedIterable<O> map(Fn<? super T, O> fn) {
        return new PreparedIterable<>(new MappingIterable<>(elements, fn));
    }

    @Override
    public <O> Elements<O> flatMap(Fn<? super T, ? extends Iterable<O>> fn) {
        return new PreparedIterable<>(new FlatMappingIterable<>(elements, fn));
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
    public PreparedIterable<T> tail() {
        if (isEmpty()) throw new NoSuchElementException();
        else return new PreparedIterable<>(new SkipLeadingIterable<>(1, elements));
    }



    @Override
    public Elements<T> repeat(int times) {
        if (isEmpty() || times == 1) return this;
        else if (times == 0) return none();
        else if (times < 0) throw new IllegalArgumentException("Can not repeat anything " + times + " times");
        else return new PreparedIterable<>(new CyclingIterable<T>(times, elements));
    }


    @Override
    public Elements<T> eval() {
        return on(collect());
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }



}
