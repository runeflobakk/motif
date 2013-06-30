package no.motif;

import static no.motif.Base.notNull;
import no.motif.f.Predicate;
import no.motif.option.Optional;
import no.motif.option.Optional.None;
import no.motif.option.Optional.Some;

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
     * @return The <code>None</code> instance.
     */
    public static <V> Optional<V> none() {
        return None.getInstance();
    }

    private Singular() {}
}
