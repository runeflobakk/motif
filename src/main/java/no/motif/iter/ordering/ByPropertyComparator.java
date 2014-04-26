package no.motif.iter.ordering;

import java.util.Comparator;

import no.motif.f.Fn;

public final class ByPropertyComparator<T, C extends Comparable<C>> implements Comparator<T> {

    private final Fn<T, C> fn;

    public ByPropertyComparator(Fn<T, C> fn) {
        this.fn = fn;
    }

    @Override
    public int compare(T first, T second) {
        return fn.$(first).compareTo(fn.$(second));
    }
}
