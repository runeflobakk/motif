package no.motif.iter;

import static java.util.Collections.sort;
import static java.util.Collections.unmodifiableList;
import static no.motif.Base.toString;
import static no.motif.Iterate.by;
import static no.motif.Singular.optional;
import static no.motif.Strings.concat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import no.motif.Singular;
import no.motif.Strings;
import no.motif.f.Do;
import no.motif.f.Fn;
import no.motif.f.Fn2;
import no.motif.f.Predicate;
import no.motif.single.Optional;
import no.motif.types.Elements;

/**
 * This iterable offers operations which requires collecting (i.e.
 * iterating) the containing elements of the iterable. In
 * particular, this class holds the methods which "bridges" back
 * to the non-lazy Java Collection Framework classes.
 *
 * @param <T> The type of elements in this iterable.
 */
abstract class CollectingIterable<T> implements Elements<T>, Serializable {

    @Override
    public final <O> O reduce(O unit, Fn2<? super O, ? super T, ? extends O> reducer) {
        O reduced = unit;
        for (T element : this) reduced = reducer.$(reduced, element);
        return reduced;
    }


    @Override
    public final List<T> collect() {
        return unmodifiableList(collectIn(new ArrayList<T>()));
    }


    @Override
    public final <C extends Collection<T>> C collectIn(C collection) {
        for (T t : this) {
            collection.add(t);
        }
        return collection;
    }



    @Override
    public final <P extends Comparable<P>> List<T> sortedBy(Fn<? super T, P> property) {
        return sorted(by(property));
    }



    @Override
    public final List<T> sorted(Comparator<? super T> comparator) {
        List<T> elements = collectIn(new ArrayList<T>());
        sort(elements, comparator);
        return unmodifiableList(elements);
    }


    @Override
    public void each(Do<? super T> sideEffect) {
        for (T element : this) sideEffect.$(element);
    }


    @Override
    public boolean isEmpty() {
        return !iterator().hasNext();
    }


    @Override
    public boolean exists(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.$(t)) return true;
        return false;
    }


    @Override
    public Optional<T> head() {
        return !isEmpty() ? optional(iterator().next()) : Singular.<T>none();
    }


    @Override
    public String join() {
        return reduce("", concat);
    }


    @Override
    public String join(String separator) {
        if (isEmpty()) return "";
        return head().map(toString).append(tail().map(Strings.prepend(separator))).reduce("", concat);
    }


    /**
     * Textual description of the contents of the iterable. Have in mind that for lazy
     * implementations, using {@link #toString()} will iterate over the elements to
     * actually be able to create the description.
     */
    @Override
    public String toString() {
        return collect().toString();
    }

}
