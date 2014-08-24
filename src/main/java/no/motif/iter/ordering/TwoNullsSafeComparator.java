package no.motif.iter.ordering;

import java.io.Serializable;
import java.util.Comparator;

final class TwoNullsSafeComparator<T> implements Comparator<T>, Serializable {

    private final Comparator<T> comparator;

    TwoNullsSafeComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(T o1, T o2) {
        if (o1 == null && o2 == null) return 0;
        return comparator.compare(o1, o2);
    }

}
