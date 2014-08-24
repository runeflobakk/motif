package no.motif;

import static no.motif.Base.first;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

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
     * Get the name of a {@link java.lang.reflect.Member}, e.g.
     * method or field name.
     */
    public static final Fn<Member, String> name = new Fn<Member, String>() {
        @Override public String $(Member member) { return member.getName(); }};


    /**
     * Get the {@link Class#getName() name} of a <code>Class</code>.
     */
    public static final Fn<Class<?>, String> className = new Fn<Class<?>, String>() {
        @Override public String $(Class<?> cls) { return cls.getName(); }};


    /**
     * Get the {@link Class#getSimpleName() simple name} of a <code>Class</code>.
     */
    public static final Fn<Class<?>, String> simpleName = new Fn<Class<?>, String>() {
        @Override public String $(Class<?> cls) { return cls.getSimpleName(); }};


    /**
     * Get the {@link Class#getPackage() package} of a <code>Class</code>.
     */
    public static final Fn<Class<?>, Package> getPackage = new Fn<Class<?>, Package>() {
        @Override public Package $(Class<?> cls) { return cls.getPackage(); }};


    /**
     * Get the {@link Package#getName() package name} of a <code>Class</code>.
     */
    public static final Fn<Class<?>, String> packageName = first(getPackage).then(new Fn<Package, String>() {
        @Override public String $(Package pkg) { return pkg.getName(); }});


    /**
     * Check if an element (class, method, field, etc) is annotated with a given annotation.
     *
     * @param annotation the annotation to check for.
     */
    public static Predicate<AnnotatedElement> annotatedWith(final Class<? extends Annotation> annotation) {
        return new Predicate<AnnotatedElement>() {
            @Override public boolean $(AnnotatedElement element) { return element.isAnnotationPresent(annotation); }}; }



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
