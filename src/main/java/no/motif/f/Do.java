package no.motif.f;

import java.io.Serializable;

/**
 * This is the equivalent of a method with return type
 * <code>void</code>. It is a pseudo-function which performs
 * only side-effects.
 *
 * @param <T> The type of the value which this <code>Do</code> accepts.
 */
public interface Do<T> extends Serializable {

    void with(T value);

}
