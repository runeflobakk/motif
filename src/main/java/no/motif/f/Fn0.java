package no.motif.f;

/**
 * A function which provides a value, without
 * taking any arguments. It can be used to
 * lazily calculate a value, or yield its result based
 * on closing over some local or global value(s).
 *
 * @param <V> Type of value yielded from the function.
 */
public interface Fn0<V> {

    /**
     * Applies the function.
     *
     * @return the result from the function application.
     */
    V $();
}
