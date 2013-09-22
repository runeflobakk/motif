package no.motif;

import no.motif.f.Fn;
import no.motif.f.Predicate;

/**
 * Operations for doing reflection.
 */
public final class Reflect {


    /**
     * Evaluates if objects are of a certain type (class or interface).
     *
     * @param type The type that will satisfy the predicate.
     */
    public static Predicate<Object> isA(final Class<?> type) { return new Predicate<Object>() {
        @Override public boolean $(Object object) { return type.isInstance(object); }}; }


    /**
     * Gets the class of an object. This is the functional equivalent of
     * the {@link Object#getClass()} method, which returns an untyped
     * {@link Class Class&lt;?&gt;}.
     */
    public static final Fn<Object, Class<?>> getClass = type();


    /**
     * This is the same as {@link #getClass}, with the additional
     * possible typing of the yielded {@link Class}es.
     */
    public static <T> Fn<T, Class<? extends T>> type() { return new Fn<T, Class<? extends T>>() {
        @SuppressWarnings("unchecked") @Override public Class<? extends T> $(T object) {
            return (Class<? extends T>) object.getClass(); }}; }


    /**
     * Create instances of classes using the no-arg constructor.
     * Any checked {@link InstantiationException} or {@link IllegalAccessException}
     * will be rethrown as <code>RuntimeException</code>.
     */
    public static <T> Fn<Class<? extends T>, T> newInstance() {
        return new Fn<Class<? extends T>, T>() {
            @Override public T $(Class<? extends T> clazz) {
                try { return clazz.newInstance(); }
                catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Not able to instantiate " + clazz.getName() +
                            " because of " + e.getClass().getSimpleName() + ": " + e.getMessage(), e);
                }
            }};
    }

    private Reflect() {}
}
