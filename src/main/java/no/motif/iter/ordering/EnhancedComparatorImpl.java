package no.motif.iter.ordering;

import static no.motif.Base.isNull;
import static no.motif.Base.not;
import static no.motif.Base.notNull;

import java.util.Comparator;

import no.motif.f.Predicate;

public class EnhancedComparatorImpl<T> implements EnhancedComparator<T> {

    private final Comparator<T> comparator;

    public EnhancedComparatorImpl(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(T o1, T o2) {
        return comparator.compare(o1, o2);
    }

    @Override
    public EnhancedComparator<T> reversed() {
        return new EnhancedComparatorImpl<>(new OppositeOrderComparator<>(comparator));
    }

    @Override
    public EnhancedComparator<T> first(Predicate<? super T> prioritized) {
        return new EnhancedComparatorImpl<>(new PrioritizingComparator<>(comparator, prioritized));
    }

    @Override
    public EnhancedComparator<T> last(Predicate<? super T> last) {
        return first(not(last));
    }

    @Override
    public EnhancedComparator<T> nullsFirst() {
        return new EnhancedComparatorImpl<>(new PrioritizingComparator<>(new TwoNullsSafeComparator<>(comparator), isNull));
    }

    @Override
    public EnhancedComparator<T> nullsLast() {
        return new EnhancedComparatorImpl<>(new PrioritizingComparator<>(new TwoNullsSafeComparator<>(comparator), notNull));
    }

}
