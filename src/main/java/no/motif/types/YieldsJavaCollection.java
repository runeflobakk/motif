package no.motif.types;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.motif.Iterate;
import no.motif.f.Fn;

/**
 * Defines the methods which bridges Motif back to the
 * Java Collection Framework.
 *
 * @param <T> The type of the contained objects.
 */
public interface YieldsJavaCollection<T> {

    /**
     * Get the elements as an immutable {@link List}. This is the
     * most common way to obtain a regular implementation of a Java
     * collection.
     *
     * If you need more control on the returned {@link Collection}
     * implementation, use {@link #collectIn(Collection)}, e.g. if
     * you need a mutable collection.
     *
     * @return the elements of the iterable as an unmodifiable list.
     */
    List<T> collect();


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
    <C extends Collection<T>> C collectIn(C collection);



    /**
     * Get a sorted immutable {@link List} of the contents of this iterable.
     *
     * @param property The function to obtain the property to sort by of each element.
     * @return the elements of the iterable as a sorted list.
     */
    <P extends Comparable<P>> List<T> sortedBy(Fn<? super T, P> property);


    /**
     * Get a sorted immutable {@link List} of the contents of this iterable.
     *
     * @param comparator The comparator to use when sorting the elements.
     *                   Use {@link Iterate#byOrderingOf(Class)} to use the
     *                   natural ordering of the contained elements.
     * @return the elements of the iterable as a sorted list.
     */
    List<T> sorted(Comparator<? super T> comparator);

}
