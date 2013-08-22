package no.motif.types;

import no.motif.f.Predicate;

public interface Filterable<T> {

    /**
     * Provide a predicate that evaluates which element(s) to be
     * included.
     *
     * @param filter The predicate, when evaluates an element to false,
     *               will exclude that element.
     * @return the filtered elements.
     */
    Filterable<T> filter(Predicate<? super T> filter);
}
