package no.motif;

import static no.motif.Iterate.on;
import java.util.Objects;

import no.motif.f.Conjunction;
import no.motif.f.Disjunction;
import no.motif.f.Fn;
import no.motif.f.Predicate;

/**
 * Basic functions.
 *
 * @see Predicate
 * @see Fn
 */
public final class Base {

    /**
     * Negates a predicate.
     * @param p The predicate to negate.
     * @return The new predicate.
     */
    public static <T> Predicate<T> not(final Predicate<T> p) {
        return new Predicate<T>() { @Override public boolean $(T value) { return !p.$(value); }}; }


    /**
     * Compose a predicate from taking the result of a function and apply it to
     * another predicate.
     *
     * <pre>
     * fn(T) &isin; P
     * p(P) &isin; {true, false}
     * <b>&fnof;(T) = p(fn(T)) &isin; {true, false}</b>
     * </pre>
     *
     * <p>
     * This simple composition enables one to focus on writing {@link Fn functions}
     * for your particular domain, and reusing basic predicates provided by <em>Motif</em>.
     * </p>
     *
     * @param <P>       The type of the predicate evaluating the function result.
     * @param <T>       The type of the resulting predicate.
     * @param fn        The function which result will be evaluated by the predicate. Takes
     *                  an argument of <code>T</code>
     * @param predicate Evaluates the result from the function, i.e. a value of <code>P</code>
     * @return          A predicate of <code>T</code>
     */
    public static <T, P> Predicate<T> where(final Fn<T, P> fn, final Predicate<? super P> predicate) {
        return new Predicate<T>() { @Override public boolean $(T value) { return predicate.$(fn.$(value)); }}; }



    /**
     * Create a AND-expression of several predicates, starting with the one
     * given to this method.
     *
     * @see Conjunction
     * @see #allOf(Predicate...)
     * @param predicate The first predicate.
     * @return a new predicate which may be used to build up an AND-expression by
     *         chaining the {@link Conjunction#and(Predicate) and(anotherPredicate)} method.
     */
    public static <T> Conjunction<T> both(Predicate<T> predicate) {
        return new Conjunction<T>(predicate);
    }



    /**
     * Compose an AND-expression of several predicates.
     *
     * @see Conjunction
     * @param predicates the predicates.
     * @return a new predicate which is the conjunction (AND) of the given predicates.
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Conjunction<T> allOf(Predicate<? super T> ... predicates) {
        return new Conjunction<>(predicates);
    }



    /**
     * Create a OR-expression of several predicates, starting with the one
     * given to this method.
     *
     * @see Disjunction
     * @see #anyOf(Predicate...)
     * @param predicate The first predicate.
     * @return a new predicate which may be used to build up an OR-expression by
     *         chaining the {@link Disjunction#or(Predicate) or(anotherPredicate)} method.
     */
    public static <T> Disjunction<T> either(Predicate<T> predicate) {
        return new Disjunction<T>(predicate);
    }



    /**
     * Compose an OR-expression of several predicates.
     *
     * @see Disjunction
     * @param predicates the predicates.
     * @return a new predicate which is the disjunction (OR) of the given predicates.
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Disjunction<T> anyOf(Predicate<? super T> ... predicates) {
        return new Disjunction<>(predicates);
    }


    /**
     * Evaluate if all elements satisfies a predicate, i.e. the <em>Universal quantifier function</em>.
     * Note that the predicate
     *
     * @param predicate The predicate which will evaluate all elements.
     * @return a predicate which evaluates to <code>true</code> if all elements evaluates
     *         to true by the given predicate, false if any evaluates to <code>false</code>.
     */
    public static <E, I extends Iterable<E>> Predicate<I> all(final Predicate<? super E> predicate) {
        return new Predicate<I>() { @Override public boolean $(I iterable) {
            return on(iterable).filter(not(predicate)).isEmpty(); }};}


    /**
     * Evaluate if an element exists, i.e. the <em>Existential quantifier function</em>.
     *
     * @param element The existance-deciding predicate.
     * @return a predicate which evaluates to <code>true</code> if any element evaluates
     *         to true by the given predicate, <code>false</code> otherwise.
     */
    public static <E, I extends Iterable<E>> Predicate<I> exists(final Predicate<? super E> element) {
        return new Predicate<I>() { @Override public boolean $(I iterable) {
            return on(iterable).exists(element); }};}


    /**
     * A synonym for {@link #equalTo(Object)}.
     */
    public static <T> Predicate<T> is(T value) { return equalTo(value); }


    /**
     * Equality predicate, checks if values are equal to the given value.
     *
     * @param value the value the predicate should check for equality against.
     * @return the equality predicate.
     */
    public static <T> Predicate<T> equalTo(final T value) {
        return new Predicate<T>() { @Override public boolean $(T input) { return Objects.equals(input, value); }}; }


    public static final Predicate<Object> isNull = new Predicate<Object>() {
        @Override public boolean $(Object value) { return value == null; }};


    public static final Predicate<Object> notNull = not(isNull);


    public static final Fn<Object, String> toString = new Fn<Object, String>() {
        @Override public String $(Object value) { return String.valueOf(value); }};


    /**
     * A bridge from functions yielding arrays, to functions yielding iterables.
     *
     * @param yieldsArray the array-yielding function.
     * @return an adapted function yielding an iterable of the array yielded from the original function.
     */
    public static final <I, O> Fn<I, Iterable<O>> toIterable(final Fn<I, O[]> yieldsArray) {
        return new Fn<I, Iterable<O>>() { @Override public Iterable<O> $(I value) { return on(yieldsArray.$(value)); }}; }


    private Base() {}


}
