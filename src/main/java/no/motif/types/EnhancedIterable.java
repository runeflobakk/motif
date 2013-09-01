package no.motif.types;

import no.motif.f.Fn;
import no.motif.f.Predicate;

/**
 * This is the main type of Motif's collection API and
 * encompasses all the operations available to manipulate
 * and query the the contained elements, as well as
 * converting back to Java Collection Framework
 * collections.
 *
 *
 * @param <T> The type of the contained objects.
 * @apiviz.landmark
 */
public interface EnhancedIterable<T> extends
    Elements<T>,
    YieldsJavaCollection<T>,
    Existance<T>,
    Iterable<T>,
    Reducible<T> {


    @Override public <O> EnhancedIterable<O> map(Fn<? super T, O> function);
    @Override public EnhancedIterable<T> filter(Predicate<? super T> filter);

}
