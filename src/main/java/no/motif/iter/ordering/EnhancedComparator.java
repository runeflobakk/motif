package no.motif.iter.ordering;

import java.util.Comparator;

import no.motif.f.Predicate;

/**
 * A {@link Comparator} augmented with various methods to refine its functionality.
 */
public interface EnhancedComparator<T> extends Comparator<T> {


    /**
     * @return <code>Comparator</code> with reversed order.
     */
    EnhancedComparator<T> reversed();


    /**
     * @return a comparator which places elements matched by
     *         the given predicate first.
     */
    EnhancedComparator<T> first(Predicate<? super T> prioritized);


    /**
     * @return a comparator which places elements matched by
     *         the given predicate last.
     */
    EnhancedComparator<T> last(Predicate<? super T> last);


    /**
     * @return a <code>null</code>-safe comparator which places
     *         <code>null</code>-elements first.
     */
    EnhancedComparator<T> nullsFirst();


    /**
     * @return a <code>null</code>-safe comparator which places
     *         <code>null</code>-elements last.
     */
    EnhancedComparator<T> nullsLast();

}
