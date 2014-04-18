package no.motif.single;

import java.io.Serializable;

import no.motif.Iterate;
import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.types.Appendable;
import no.motif.types.Elements;
import no.motif.types.Filterable;
import no.motif.types.Mappable;
import no.motif.types.Prependable;


/**
 * <em>This is a V</em>, i.e. <code>A&lt;V&gt;</code>, a singular value
 * of type <code>V</code> which is always
 * considered as "present". Most of the operations from {@link Optional}
 * are available, except for the ones implying that the value may not be
 * present. Unlike <code>Optional</code>, calling {@link #get()} on
 * <code>A&lt;V&gt;</code> will <em>never</em> throw an exception.
 *
 * @param <V>
 */
public interface A<V> extends Iterable<V>, Mappable<V>, Filterable<V>, Appendable<V>, Prependable<V>, Serializable {

    /**
     *
     * @return the contained value. Unlike <code>Optional</code>, calling
     *         {@link #get()} on <code>A&lt;V&gt;</code> will <em>never</em>
     *         throw an exception.
     */
    V get();


    /**
     * Map the contained to another value. This will yield an <code>Optional</code> of
     * the other type, which is "present" for any non-null value yielded from the
     * mapping.
     */
    @Override
    <O> Optional<O> map(Fn<? super V, O> mapper);

    /**
     * Map the value to another value. This will yield an <code>Optional</code> of
     * the other type. The given {@link Predicate} decides if the resulting value
     * is "present".
     *
     * @see #map(Fn)
     */
    <O> Optional<O> map(Predicate<? super O> isPresent, Fn<? super V, O> mapper);


    @Override
    Optional<V> filter(Predicate<? super V> filter);


    /**
     * Split the value into multiple values.
     * <p>
     * Due to limitations of the type system in Java, it is not possible to overload this
     * method to accept both functions yielding an {@link Iterable} (as this method does)
     * as well as functions yielding an array. It is recommended to prefer implementing
     * {@link Fn functions} yielding Iterables. If implementing an array-yielding
     * function is most appropriate for your case, and you wish to use the function to split
     * an optional value using this method, it is possible to adapt the function using
     * {@link Iterate#toIterable(Fn)}
     * </p>
     *
     * @param splitter a function yielding an iterable.
     * @return the elements.
     */
    <O> Elements<O> split(Fn<? super V, ? extends Iterable<O>> splitter);

}
