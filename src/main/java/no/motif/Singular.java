package no.motif;

import static no.motif.Base.notNull;
import no.motif.f.Predicate;
import no.motif.single.A;
import no.motif.single.Optional;
import no.motif.single.Optional.None;
import no.motif.single.Optional.Some;

/**
 * Operations for working with single values.
 */
public final class Singular {

    /**
     * Wrap an object or <code>null</code> in an <code>Optional</code>.
     *
     * @param value The object to wrap. May be <code>null</code>.
     * @param <V>   The type of the wrapped object.
     *
     * @return {@link Some} if <code>value</code> is an object,
     *         {@link None} if <code>value</code> is <code>null</code>
     *
     * @see Optional
     * @see Some
     * @see None
     */
    public static <V> Optional<V> optional(V value) {
        return Optional.resolve(notNull, value); }


    /**
     * Wrap an object in an <code>Optional</code>, using a predicate to
     * determine if the value should be treated as defined.
     *
     * @param <V>       The type of the wrapped object.
     * @param isPresent A {@link Predicate} function returning true if
     *                  the value is considered defined or 'present'. This
     *                  function must be able to handle <code>null</code>.
     *
     * @param value     The object to wrap. May be <code>null</code>.
     *
     *
     * @return          {@link Some} if <code>value</code> is defined,
     *                  {@link None} otherwise.
     *
     * @see Optional
     * @see Some
     * @see None
     */
    public static <V> Optional<V> optional(Predicate<? super V> isPresent, V value) {
        return Optional.resolve(isPresent, value); }



    /**
     * This will create {@link A A&lt;V&gt;}, for a value which is <em>not optional</em>,
     * but you want to access the API for doing operations as with an {@link Optional}.
     * Use this instead of an awkward call to {@link #optional(Object)} when the value is
     * not in fact optional.
     *
     * @param value The value, may be anything or <code>null</code>. No
     * @return The value wrapped in an {@link A}.
     */
    public static <V> A<V> the(V value) {
        return Optional.some(value);
    }


    /**
     * @return The <code>None</code> instance.
     */
    public static <V> Optional<V> none() {
        return None.getInstance();
    }

    private Singular() {}
}
