package no.motif.iter.ordering;

import java.util.Comparator;

final class OppositeOrderComparator<T> implements Comparator<T> {

    private final Comparator<T> comparator;

    OppositeOrderComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(T o1, T o2) {
        return comparator.compare(o2, o1);
    }

}
