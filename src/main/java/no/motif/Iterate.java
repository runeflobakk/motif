package no.motif;

import static java.util.Arrays.asList;
import static no.motif.Singular.optional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.iter.PreparedIterable;
import no.motif.types.Elements;


/**
 * Operations for containers, e.g. arrays,
 * {@link List lists}, {@link Set sets}, or any implementation
 * of {@link Iterable}. Can also
 * {@link #on(CharSequence) treat Strings as a container of Characters}.
 *
 */
public final class Iterate {


    /**
     * Manipulate CharSequences/Strings as if they were iterables of
     * Characters.
     *
     * @param string The string
     * @return {@link Elements} characters
     */
    public static Elements<Character> on(CharSequence string) {
        if (string == null) return PreparedIterable.empty();
        List<Character> charList = new ArrayList<>(string.length());
        for (char c : string.toString().toCharArray()) charList.add(c);
        return newInstance(charList);
    }


    /**
     * Work with multiple elements.
     *
     * @param elements the elements to manipulate as vararg/array.
     * @return {@link Elements}
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Elements<T> on(T ... elements) {
        return newInstance(elements != null ? asList(elements) : null);
    }


    public static <K, V> Elements<Map.Entry<K, V>> on(Map<K, V> map) {
        return on(map != null ? map.entrySet() : null);
    }


    /**
     * Work with multiple elements.
     *
     * @param elements the elements to manipulate as an {@link Iterable}.
     * @return {@link Elements}
     */
    public static <T> Elements<T> on(Iterable<T> elements) {
        return elements instanceof Elements ? (Elements<T>) elements : newInstance(elements);
    }






    private static <T> Elements<T> newInstance(Iterable<T> elements) {
        if (elements == null) return PreparedIterable.empty();
        else return new PreparedIterable<T>(elements);
    }



    /**
     * Create a {@link java.util.Comparator} from an {@link Fn}, typically to
     * sort using a {@link Comparable} property of a type.
     *
     * @param property the {@link Fn} which obtains the comparable property
     *
     * @return a {@link Comparator} using the value yielded from the given {@link Fn}.
     */
    public static <T, P extends Comparable<P>> Comparator<T> by(final Fn<T, P> property) {
        return new Comparator<T>() { @Override public int compare(T first, T second) {
            return property.$(first).compareTo(property.$(second)); }}; }


    /**
     * Convert a type with natural ordering, i.e. implements {@link Comparable}, to
     * an external {@link Comparator}. This is a little quirk to provide a typesafe
     * way to sort iterables containing <code>Comparables</code>, instead of risking
     * a runtime ClassCastException. Pass this to the
     * {@link no.motif.iter.CollectingIterable#sorted(Comparator) .sorted(..)}
     * method to get elements as a sorted list.
     *
     * @param comparableType The {@link Comparable} type to create a {@link Comparator} from.
     * @return the <code>Comparator</code>
     */
    public static <T extends Comparable<T>> Comparator<T> byOrderingOf(Class<T> comparableType) {
        return by(NOP.<T>fn()); }


    /**
     * Evaluates if an {@link Iterable} is empty.
     */
    public static final Predicate<Iterable<?>> empty = new Predicate<Iterable<?>>() {
        @Override public boolean $(Iterable<?> iterable) { return on(iterable).isEmpty(); }};


    /**
     * The {@link Iterator#hasNext()} method as a function.
     */
    public static final Predicate<Iterator<?>> hasNext = new Predicate<Iterator<?>>() {
        @Override public boolean $(Iterator<?> iterator) { return iterator.hasNext(); }};


    /**
     * @return The {@link Iterator#next()} as a function.
     */
    public static final <T> Fn<Iterator<T>, T> next() { return new Fn<Iterator<T>, T>() {
        @Override public T $(Iterator<T> iterator) { return iterator.next(); }};};



    /**
     * Using a function which takes an object and returns another object of the same type,
     * recursively call this function until it yields <code>null</code>, and ultimately yield the
     * last object.
     *
     * @param next The function which takes an object and yields another object of the same type.
     *             This is typically used for objects implementing a linked list-like structure,
     *             where each object refers to the next in a chain.
     *
     * @return A function which will descend the chain and yield the last object. If the given
     *         <code>next</code> {@link Fn} never returns <code>null</code>, calling this
     *         returned function will result in an infinite recursion.
     */
    public static <T> Fn<T, T> last(final Fn<? super T, ? extends T> next) {
        return new Fn<T, T>() {
        @Override public T $(T value) {
            return optional(next.$(value)).map(this).getOrElse(value);
        }};
    }


    /**
     * A bridge from functions yielding arrays, to functions yielding iterables.
     *
     * @param yieldsArray the array-yielding function.
     * @return an adapted function yielding an iterable of the array yielded from the original function.
     */
    public static <I, O> Fn<I, Iterable<O>> toIterable(final Fn<I, O[]> yieldsArray) {
        return new Fn<I, Iterable<O>>() { @Override public Iterable<O> $(I value) { return on(yieldsArray.$(value)); }}; }



    private Iterate() {}

}
