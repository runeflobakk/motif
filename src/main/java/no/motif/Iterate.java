package no.motif;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.iter.PreparedIterable;


/**
 * Operations for containers, e.g. arrays,
 * {@link List lists}, {@link Set sets}, or any implementation
 * of {@link Iterable}. Can also
 * {@link #on(CharSequence) treat Strings as a container of Characters}.
 */
public final class Iterate {


    /**
     * Manipulate CharSequences/Strings as if they were iterables of
     * Characters.
     *
     * @param string The string
     * @return {@link PreparedIterable} characters
     */
    public static PreparedIterable<Character> on(CharSequence string) {
        if (string == null) return PreparedIterable.empty();
        List<Character> charList = new ArrayList<>(string.length());
        for (char c : string.toString().toCharArray()) charList.add(c);
        return newInstance(charList);
    }


    /**
     * Work with multiple elements.
     *
     * @param elements the elements to manipulate as vararg/array.
     * @return {@link PreparedIterable}
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> PreparedIterable<T> on(T ... elements) {
        return newInstance(elements != null ? asList(elements) : null);
    }


    /**
     * Work with multiple elements.
     *
     * @param elements the elements to manipulate as an {@link Iterable}.
     * @return {@link PreparedIterable}
     */
    public static <T> PreparedIterable<T> on(Iterable<T> elements) {
        return elements instanceof PreparedIterable ? (PreparedIterable<T>) elements : newInstance(elements);
    }


    private static <T> PreparedIterable<T> newInstance(Iterable<T> elements) {
        if (elements == null) return PreparedIterable.empty();
        else return new PreparedIterable<T>(elements);
    }



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




    private Iterate() {}

}
