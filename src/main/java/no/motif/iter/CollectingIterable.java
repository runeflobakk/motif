package no.motif.iter;

import static java.util.Collections.unmodifiableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.motif.f.Fn2;
import no.motif.f.Predicate;
import no.motif.types.Reducible;

/**
 * This iterable offers operations which requires collecting (i.e.
 * iterating) the containing elements of the iterable. In
 * particular, this class holds the methods which "bridges" back
 * to the non-lazy Java Collection Framework classes.
 *
 * @param <T> The type of elements in this iterable.
 */
public abstract class CollectingIterable<T> implements Iterable<T>, Reducible<T>, Serializable {

    @Override
    public final <O> O reduce(O unit, Fn2<? super O, ? super T, ? extends O> reducer) {
        O reduced = unit;
        for (T element : this) reduced = reducer.$(reduced, element);
        return reduced;
    }

    /**
     * @return the contents of the iterable as an unmodifiable list.
     */
    public final List<T> collect() {
        return unmodifiableList(collectIn(new ArrayList<T>()));
    }

    /**
     * Collects the elements in this iterable in the given collection.
     * The caller is responsible for supplying an appropriate collection
     * implementation for what behavior that is expected. Keep in mind that
     * {@link Set sets} does not allow duplicates, and {@link HashSet many implementations}
     * also does not maintain the order of the elements. The collection must obviously
     * support the {@link Collection#add(Object) add(..)} operation. Unless you
     * have particular needs for the resulting collection, consider using
     * the {@link #collect()} method instead.
     *
     * @param collection The collection to add the elements to.
     * @return the given collection is returned.
     */
    public final <C extends Collection<T>> C collectIn(C collection) {
        for (T t : this) {
            collection.add(t);
        }
        return collection;
    }


    /**
     * @return <code>true</code> if empty, <code>false</code> otherwise.
     */
    public boolean isEmpty() {
        return !iterator().hasNext();
    }


    /**
     * Decide if an element exist.
     *
     * @param predicate evaluates each element.
     * @return <code>true</code> once the predicate evaluates to true,
     *         otherwise <code>false</code>.
     */
    public boolean exists(Predicate<? super T> predicate) {
        for (T t : this) if (predicate.$(t)) return true;
        return false;
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
