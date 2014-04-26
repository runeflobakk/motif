package no.motif.iter.ordering;

import java.util.Comparator;

import no.motif.f.Predicate;

class PrioritizingComparator<T> implements Comparator<T> {

    private final Comparator<T> comparator;
    private final Predicate<? super T> prioritized;

    PrioritizingComparator(Comparator<T> comparator, Predicate<? super T> prioritized) {
        this.comparator = comparator;
        this.prioritized = prioritized;
    }

    @Override
    public int compare(T o1, T o2) {
        boolean pri1 = prioritized.$(o1);
        boolean pri2 = prioritized.$(o2);
        if (pri1 && !pri2) return -1;
        if (pri2 && !pri1) return 1;
        return comparator.compare(o1, o2);
    }

}
