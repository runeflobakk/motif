package no.motif.iter;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.motif.f.Fn2;

/**
 * This iterable offers operations which requires collecting (i.e.
 * iterating) the containing elements of the iterable.
 *
 * @param <T> The type of elements in this iterable.
 */
public abstract class CollectingIterable<T> implements Iterable<T> {

    /**
     * Reduce the elements into one value.
     *
     * @param unit The start value for the reduction. The unit should in general (but
     *             is not required to) be such that when combined with any element in
     *             the iterable, yield the iterable's element.
     *
     * @param reducer the function which first argument is the accumulated reduction
     *                througout the iteration, and second argument the value to include
     *                in the reduction.
     *
     * @return The resulting value from the reduction.
     */
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
     * Textual description of the contents of the iterable. Have in mind that for lazy
     * implementations, using {@link #toString()} will iterate over the elements to
     * actually be able to create the description.
     */
    @Override
    public String toString() {
        return collect().toString();
    }

}
