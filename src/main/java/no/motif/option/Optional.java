package no.motif.option;

import static no.motif.Base.notNull;
import java.util.Iterator;
import java.util.NoSuchElementException;

import no.motif.Singular;
import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.iter.EmptyIterator;
import no.motif.iter.SingularIterator;
import no.motif.types.Mappable;

/**
 * An <code>Optional</code> wraps a value that is either defined or
 * undefined. Usually, undefined means <code>null</code>, and defined means
 * an object instance, but the distinction between defined and undefined can
 * be customized with a {@link Predicate}.
 *
 * @param <V> The type of the wrapped object.
 *
 */
public abstract class Optional<V> implements Iterable<V>, Mappable<V> {


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
        public final V getOrElse(V fallback) {
            return get();
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
        public final V getOrElse(V fallback) {
            return fallback;
        }

        @Override
        public <O> Optional<O> map(Fn<? super V, O> mapper) {
            return None.getInstance();
        }

        @Override
        public <O> Optional<O> map(Predicate<? super O> isPresent, Fn<? super V, O> mapper) {
            return None.getInstance();
        }

    }



    /**
     * Obtain the value contained in the <code>Optional</code>. To use
     * this method one <em>must</em> first determine that the
     * <code>Optional</code> does in fact hold a value.
     *
     * To obtain the wrapped value, it is in general preferable to favor
     * <em>iterating</em> or using {@link #getOrElse(Object)}, which will never
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
    public abstract <O> Optional<O> map(Predicate<? super O> isPresent, Fn<? super V, O> transformer);


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
    public abstract V getOrElse(V fallback);

}