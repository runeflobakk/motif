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
 * Operations for iterables, e.g.
 * {@link List lists}, {@link Set sets}, or any implementation
 * of {@link Iterable}.
 *
 * @see PreparedIterable
 */
public final class Iterate {


    public static PreparedIterable<Character> on(CharSequence chars) {
        if (chars == null) return PreparedIterable.empty();
        List<Character> charList = new ArrayList<>(chars.length());
        for (char c : chars.toString().toCharArray()) charList.add(c);
        return newInstance(charList);
    }


    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> PreparedIterable<T> on(T ... elements) {
        return newInstance(elements != null ? asList(elements) : null);
    }


    public static <T> PreparedIterable<T> on(Iterable<T> elements) {
        return elements instanceof PreparedIterable ? (PreparedIterable<T>) elements : newInstance(elements);
    }

    private static <T> PreparedIterable<T> newInstance(Iterable<T> elements) {
        if (elements == null) return PreparedIterable.empty();
        else return new PreparedIterable<T>(elements);
    }


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
