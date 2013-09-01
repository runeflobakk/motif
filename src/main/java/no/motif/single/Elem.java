package no.motif.single;

import java.io.Serializable;
import java.util.Objects;

import no.motif.f.Fn;

/**
 * A simple container for a value coupled with an index.
 *
 * @param <T> The type of the containing value.
 */
public final class Elem<T> implements Serializable {

    public final int index;
    public final T value;

    public static <T> Elem<T> of(int index, T value) {
        return new Elem<T>(index, value);
    }

    private Elem(int index, T value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Elem) {
            Elem<?> another = (Elem<?>) o;
            return this.index == another.index && Objects.equals(this.value, another.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, value);
    }

    @Override
    public String toString() {
        return "[" + index + ": " + value + "]";
    }

    public static <T> Fn<T, Elem<T>> withIndex(final int index) { return new Fn<T, Elem<T>>() {
        @Override public Elem<T> $(T value) { return new Elem<T>(index, value); }}; }
}
