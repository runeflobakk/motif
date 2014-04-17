package no.motif.single;

import static no.motif.Base.notNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import no.motif.Iterate;
import no.motif.Singular;
import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.iter.EmptyIterator;
import no.motif.iter.SingularIterator;
import no.motif.types.Appendable;
import no.motif.types.Elements;
import no.motif.types.Filterable;
import no.motif.types.Mappable;
import no.motif.types.Prependable;

/**
 * An <code>Optional</code> wraps a value that is either defined or
 * undefined. Usually, undefined means <code>null</code>, and defined means
 * an object instance, but the distinction between defined and undefined can
 * be customized with a {@link Predicate}.
 *
 * @param <V> The type of the wrapped object.
 */
public abstract class Optional<V>
    implements Iterable<V>, Mappable<V>, Filterable<V>, Appendable<V>, Prependable<V>, Serializable {


    /**
     * <h1>*** Not part of the public API! ***</h1>
     * Factory method for resolving a value to {@link Some} or {@link None}.
     *
     * @see Singular#optional(Object)
     * @see Singular#optional(Predicate, Object)
     * @see Singular#none()
     */
    public static <V> Optional<V> resolve(Predicate<? super V> isPresent, V value) {
        if (isPresent.$(value)) {
            return new Some<V>(value);
        } else {
            return None.getInstance();
        }
    }


    /**
     * Wrapper for a 'defined' value. You never under normal
     * circumstances refer to this type.
     *
     * @param <V> The type of the wrapped object.
     */
    public static final class Some<V> extends Optional<V> {

        private final V value;

        private Some(V value) {
            this.value = value;
        }

        @Override
        public final Iterator<V> iterator() {
            return new SingularIterator<V>(value);
        }

        @Override
        public final boolean isSome() {
            return true;
        }

        @Override
        public final V orElse(V fallback) {
            return get();
        }

        @Override
        public V orNull() {
            return value;
        }

        @Override
        public Optional<V> or(Optional<V> otherOptional) {
            return this;
        }

        @Override
        public <O> Optional<O> map(Fn<? super V, O> mapper) {
            O mapped = mapper.$(this.value);
            return resolve(notNull, mapped);
        }

        @Override
        public <O> Optional<O> map(Predicate<? super O> isPresent, Fn<? super V, O> mapper) {
            O mapped = mapper.$(this.value);
            return resolve(isPresent, mapped);
        }

        @Override
        public <O> Optional<O> flatMap(Fn<? super V, Optional<O>> mapper) {
            return mapper.$(value);
        }

        @Override
        public Optional<V> filter(Predicate<? super V> accepted) {
            return resolve(accepted, value);
        }

        @Override
        public <O> Elements<O> split(Fn<? super V, ? extends Iterable<O>> splitter) {
            return Iterate.on(splitter.$(value));
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Some) {
                Some<?> other = (Some<?>) o;
                return Objects.equals(this.value, other.value);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }

        @Override
        public String toString() {
            return "Some(" + value + ")";
        }

    }

    /**
     * Wrapper for an 'undefined' value. You never under normal
     * circumstances refer to this type.
     *
     * @param <V> The type of the undefined value.
     */
    public static final class None<V> extends Optional<V> {

        private static final None<?> INSTANCE = new None<Object>();

        @SuppressWarnings("unchecked")
        public static <V> None<V> getInstance() {
            return (None<V>) INSTANCE;
        }

        private None() {}

        @Override
        public final Iterator<V> iterator() {
            return EmptyIterator.instance();
        }

        @Override
        public final boolean isSome() {
            return false;
        }

        @Override
        public final V orElse(V fallback) {
            return fallback;
        }

        @Override
        public V orNull() {
            return null;
        }

        @Override
        public Optional<V> or(Optional<V> otherOptional) {
            return otherOptional;
        }

        @Override
        public <O> Optional<O> map(Fn<? super V, O> mapper) {
            return None.getInstance();
        }

        @Override
        public <O> Optional<O> map(Predicate<? super O> isPresent, Fn<? super V, O> mapper) {
            return None.getInstance();
        }

        @Override
        public <O> Optional<O> flatMap(Fn<? super V, Optional<O>> mapper) {
            return None.getInstance();
        }

        @Override
        public Optional<V> filter(Predicate<? super V> filter) {
            return None.getInstance();
        }

        @Override
        public <O> Elements<O> split(Fn<? super V, ? extends Iterable<O>> splitter) {
            return Iterate.none();
        }

        @Override
        public String toString() {
            return "None";
        }

    }



    /**
     * Obtain the value contained in the <code>Optional</code>. To use
     * this method one <em>must</em> first determine that the
     * <code>Optional</code> does in fact hold a value.
     *
     * To obtain the wrapped value, it is in general preferable to favor
     * <em>iterating</em> or using {@link #orElse(Object)}, which will never
     * throw an exception.
     *
     * @throws NoSuchElementException if this method is called on an
     *         <code>Optional</code> without a defined value.
     *
     * @see #isSome()
     */
    public final V get() {
        return iterator().next();
    }



    /**
     * Map this <code>Optional</code> to another type of <code>Optional</code>.
     *
     * @param mapper      A {@link Fn function} which transforms
     *                    (or maps) the wrapped value to another value. This
     *                    function will only ever be called if the
     *                    <code>Optional</code> holds a defined value.
     *
     * @return A new <code>Optional</code> from which the new value can be
     *         obtained, if it is defined.
     */
    @Override
    public abstract <O> Optional<O> map(Fn<? super V, O> mapper);


    /**
     * Map this <code>Optional</code> to another type of <code>Optional</code>.
     *
     * @see #map(Fn)
     * @see #resolve(Predicate, Object)
     */
    public abstract <O> Optional<O> map(Predicate<? super O> isPresent, Fn<? super V, O> mapper);


    public abstract <O> Optional<O> flatMap(Fn<? super V, Optional<O>> mapper);


    @Override
    public abstract Optional<V> filter(Predicate<? super V> filter);


    /**
     * @return <code>true</code> if the <code>Optional</code> holds a
     *         defined value, or <code>false</code> otherwise, which usually
     *         means that the wrapped value is <code>null</code>.
     */
    public abstract boolean isSome();


    /**
     *
     * @param fallback A value to return if called on a {@link None}.
     * @return The wrapped value, or the fallback value if it is undefined.
     */
    public abstract V orElse(V fallback);


    /**
     * Shortcut for {@link #orElse(Object) .orElse(null)}
     * @return The wrapped value, or <code>null</code> if it is undefined.
     */
    public abstract V orNull();

    /**
     * @return If this <code>Optional</code> is not defined, the given <code>otherOptional</code>
     *         is returned, otherwise the original <code>Optional</code> is returned as-is.
     */
    public abstract Optional<V> or(Optional<V> otherOptional);


    /**
     * Split an optional value, if defined, into multiple values.
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
     * @return the elements, or empty iterable if undefined value.
     */
    public abstract <O> Elements<O> split(Fn<? super V, ? extends Iterable<O>> splitter);


    @Override
    public Elements<V> append(Iterable<? extends V> trailingElements) {
        return Iterate.on(this).append(trailingElements);
    }

    @Override
    public Elements<V> append(V value) {
        return Iterate.on(this).append(value);
    }

    @Override
    public Elements<V> prepend(Iterable<? extends V> leadingElements) {
        return Iterate.on(this).prepend(leadingElements);
    }

    @Override
    public Elements<V> prepend(V value) {
        return Iterate.on(this).prepend(value);
    }


    private Optional() {}
}
