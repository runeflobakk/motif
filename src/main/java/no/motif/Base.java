package no.motif;

import static no.motif.Iterate.on;

import java.util.Collection;
import java.util.Objects;

import no.motif.f.Do;
import no.motif.f.Fn;
import no.motif.f.Fn0;
import no.motif.f.Fn2;
import no.motif.f.Predicate;
import no.motif.f.Predicate.Always;
import no.motif.f.combine.Conjunction;
import no.motif.f.combine.ConjunctionPremise;
import no.motif.f.combine.Disjunction;
import no.motif.f.combine.DisjunctionPremise;
import no.motif.f.combine.DoChain;
import no.motif.f.combine.Fn2Chain;
import no.motif.f.combine.FnChain;
import no.motif.f.combine.RunnableChain;
import no.motif.f.combine.When;
import no.motif.f.combine.Where;
import no.motif.f.impl.Constant;
import no.motif.f.impl.Throw;
import no.motif.iter.ExtractingIterable;
import no.motif.types.Elements;

/**
 * Basic functions.
 *
 * @see Predicate
 * @see Fn
 */
public final class Base {

    /**
     * Shorthand for {@link #not(Predicate) not(}{@link #equalTo(Object) equalTo(o))}.
     *
     * @see #not(Predicate)
     * @see #equalTo(Object)
     */
    public static <T> Predicate<T> not(T o) { return not(equalTo(o)); }


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
        return new Where<T, P>(fn, predicate); }



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
     * Create a AND-expression of several {@link Fn0 Fn0&lt;Boolean&gt;s}, starting with the one
     * given to this method.
     *
     * @see ConjunctionPremise
     * @param premise The first <code>Fn0&lt;Boolean&gt;</code>.
     * @return a new <code>Fn0&lt;Boolean&gt;</code> which may be used to build up an AND-expression by
     *         chaining the {@link ConjunctionPremise#and(Fn0) and(anotherPremise)} method.
     */
    public static ConjunctionPremise both(Fn0<Boolean> premise) {
        return new ConjunctionPremise(premise);
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
     * Compose an AND-expression of several {@link Fn0 Fn0&lt;Boolean&gt;s}.
     *
     * @see ConjunctionPremise
     * @param premises the <code>Fn0&lt;Boolean&gt;s</code>.
     * @return a new <code>Fn0&lt;Boolean&gt;</code> which is the conjunction
     *         (AND) of the given premises.
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static ConjunctionPremise allOf(Fn0<Boolean> ... premises) {
        return new ConjunctionPremise(premises);
    }



    /**
     * Shorthand for {@link #either(Predicate) either(}{@link #equalTo(Object) equalTo(o))}.
     *
     * @see #either(Predicate)
     * @see Disjunction
     */
    public static <T> Disjunction<T> either(T o) { return either(equalTo(o)); }

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
     * Create a OR-expression of several {@link Fn0 Fn0&lt;Boolean&gt;s},
     * starting with the one given to this method.
     *
     * @see DisjunctionPremise
     * @param premise The first <code>Fn0&lt;Boolean&gt;</code>.
     * @return a new <code>Fn0&lt;Boolean&gt;</code> which may be used to build up an OR-expression by
     *         chaining the {@link DisjunctionPremise#or(Fn0) or(anotherPremise)} method.
     */
    public static DisjunctionPremise either(Fn0<Boolean> premise) {
        return new DisjunctionPremise(premise);
    }


    /**
     * Shorthand for
     * {@link #anyOf(Predicate...) anyOf(}{@link #equalTo(Object) equalTo(o1), equalTo(o2), ..., equalTo(on))}
     *
     * @param objects the candidate objects for equivalence check.
     * @see Disjunction
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Disjunction<T> anyOf(T ... objects) {
        @SuppressWarnings("unchecked")
        Predicate<T>[] equalToPredicates = on(objects)
            .map(new Fn<T, Predicate<T>>() { @Override public Predicate<T> $(T o) { return equalTo(o); }})
            .collect().toArray(new Predicate[objects.length]);
        return anyOf(equalToPredicates);
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
     * Compose an OR-expression of several {@link Fn0 Fn0&lt;Boolean&gt;s}.
     *
     * @see DisjunctionPremise
     * @param premises the <code>Fn0&lt;Boolean&gt;s</code>.
     * @return a new <code>Fn0&lt;Boolean&gt;s</code> which is the disjunction (OR) of the given premises.
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static DisjunctionPremise anyOf(Fn0<Boolean> ... premises) {
        return new DisjunctionPremise(premises);
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
     * Shorthand for {@link #exists(Predicate) exists(}{@link #equalTo(Object) equalTo(element))}
     *
     * @param element The element to look for in an {@link Iterable}.
     * @return a predicate which evaluates to <code>true</code> if an element equal to the given
     *         element is found, <code>false</code> otherwise.
     */
    public static <E, I extends Iterable<E>> Predicate<I> exists(E element) { return exists(equalTo(element)); }


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
     */
    public static <T> Predicate<T> equalTo(final T value) { return equalTo(always(value)); }


    /**
     * Equality predicate, checks if values are equal to the value computed from the given {@link Fn0}.
     *
     * @param value the {@link Fn0} which will compute the value to check for equality against on each
     *              application of the predicate.
     */
    public static <T> Predicate<T> equalTo(final Fn0<? super T> value) {
        return new Predicate<T>() { @Override public boolean $(T input) {
            return Objects.equals(input, value.$()); }}; }


    /**
     * Predicate evaluating if values are less than a given value.
     *
     * @param value the right operand, i.e. the predicate will evaluate
     *              if values are less than this value.
     * @return the "<em>&lt; value</em>" predicate
     */
    public static <T extends Comparable<? super T>> Predicate<T> lessThan(final T value) {
        return new Predicate<T>() { @Override public boolean $(T candidate) { return candidate.compareTo(value) < 0; }}; }


    /**
     * Predicate evaluating if values are equal to or less than a given value.
     *
     * @param value the right operand, i.e. the predicate will evaluate
     *              if values are equal to or less than this value.
     * @return the "<em>=&lt; value</em>" predicate
     */
    public static <T extends Comparable<? super T>> Predicate<T> equalOrLessThan(T value) {
        return either(equalTo(value)).or(lessThan(value)); }



    /**
     * Predicate evaluating if values are greater than a given value.
     *
     * @param value the right operand, i.e. the predicate will evaluate
     *              if values are greater than this value.
     * @return the "<em>&gt; value</em>" predicate
     */
    public static <T extends Comparable<? super T>> Predicate<T> greaterThan(final T value) {
        return new Predicate<T>() { @Override public boolean $(T candidate) { return candidate.compareTo(value) > 0; }}; }


    /**
     * Predicate evaluating if values are equal to or greater than a given value.
     *
     * @param value the right operand, i.e. the predicate will evaluate
     *              if values are equal to or greater than this value.
     * @return the "<em>&gt;= value</em>" predicate
     */
    public static <T extends Comparable<? super T>> Predicate<T> equalOrGreaterThan(T value) {
        return either(equalTo(value)).or(greaterThan(value)); }



    /**
     * Yields <code>true</code> for <code>null</code> values.
     */
    public static final Predicate<Object> isNull = new Predicate<Object>() {
        @Override public boolean $(Object value) { return value == null; }};


    /**
     * Yields <code>true</code> for all non-<code>null</code> values.
     */
    public static final Predicate<Object> notNull = not(isNull);



    /**
     * A function to extract/derive several values from one object. The result of the
     * extraction is an {@link Elements}s container of the least common supertype of
     * the extractor {@link Fn}s' return types.
     *
     * @param extractors the {@link Fn}s which will extract values from each object passed
     *        to the function.
     *
     * @return the composed function which yields.
     */
    @SafeVarargs
    public static <T, E> Fn<T, Elements<E>> extract(final Fn<? super T, ? extends E> ... extractors) {
        return new Fn<T, Elements<E>>() { @Override public Elements<E> $(T value) {
                return on(new ExtractingIterable<T, E>(value, on(extractors))); }}; }


    /**
     * Yields the {@link String#valueOf(Object) string representation} of
     * any object.
     */
    public static final Fn<Object, String> toString = new Fn<Object, String>() {
        @Override public String $(Object value) { return String.valueOf(value); }};


    /**
     * Yields the {@link Object#hashCode() hashCode} for objects, or 0 if <code>null</code>.
     */
    public static final Fn<Object, Integer> hashCode = new Fn<Object, Integer>() {
        @Override public Integer $(Object o) { return o != null ? o.hashCode() : 0; }};


    /**
     * Creates a "guarded" {@link Fn} using a {@link Predicate} to evaluate if the
     * argument should be passed to the wrapped <code>Fn</code>. The function created
     * by this method is essentially this ternary expression:
     * <pre> condition(v) ? f(v) : null;</pre>
     * To fall back to another value than <code>null</code>, call either {@link When#orElse(Fn) .orElse(Fn)}
     * or {@link When#orElse(Object) .orElse(other)} on the returned object.
     *
     * @param condition Only when this evaluates to <code>true</code> will the function argument
     *                  be applied to the given {@link Fn}.
     * @param fn        The {@link Fn} to apply if <code>condition</code> evaluates to <code>true</code>
     */
    public static <I, O> When<I, O> when(Predicate<? super I> condition, Fn<? super I, ? extends O> fn) {
        return new When<>(condition, fn);
    }


    /**
     * Compose a chain of several functions into one {@link Fn}, where the result of
     * each function is passed to its successor, and the last will yield the
     * actual result of the chain. Use {@link FnChain#then(Fn) .then(Fn)} to append
     * functions to the chain.
     *
     * <p><strong>Note:</strong> The chain does no internal inspection of the intermediate
     * results passed to the next function, which means that any function which may
     * return <code>null</code>, <em>must</em> have a <code>null</code>-safe successor
     * function.
     *
     * @param fn The first function in the chain.
     * @return the given function as the first function in a chain.
     */
    public static final <I, O> FnChain<I, O> first(Fn<I, O> fn) {
        return FnChain.chain(fn);
    }


    /**
     * Compose a chain of several functions into one {@link Fn2}, where the result of
     * each function is passed to its successor, and the last will yield the
     * actual result of the chain. Use {@link Fn2Chain#then(Fn) .then(Fn)} to append
     * functions to the chain.
     *
     * <p><strong>Note:</strong> The chain does no inspection of the intermediate
     * results passed to the next function, which means that any function which may
     * return <code>null</code>, <em>must</em> have a <code>null</code>-safe successor
     * function.
     *
     * @param fn2 The first function in the chain.
     * @return the given function as the first function in a chain.
     */
    public static final <I1, I2, O> Fn2Chain<I1, I2, O, O> first(Fn2<I1, I2, O> fn2) {
        return new Fn2Chain<>(fn2, NOP.<O>fn());
    }


    /**
     * Compose several {@link Runnable}s as a single Runnable which will
     * execute the given Runnables in sequence.
     *
     * @param runnable The first Runnable.
     * @return the given Runnable as the first Runnable in a sequence.
     */
    public static final RunnableChain first(Runnable runnable) {
        return new RunnableChain(runnable, NOP.runnable);
    }


    /**
     * Chain several {@link Do}s to be executed in sequence.
     *
     * @param action The first {@link Do} to execute.
     * @return The given <code>Do</code> as the first <code>Do</code> in a sequence.
     */
    public static <V> DoChain<V> first(Do<V> action) {
        return new DoChain<>(action, NOP.doNothing);
    }


    /**
     * Create a function which always yields the given value.
     *
     * @param value The value to yield.
     */
    public static <I1, I2, V> Constant<I1, I2, V> always(V value) { return new Constant<>(value); }


    /**
     * Create a predicate which always yields the given <code>boolean</code> value.
     *
     * @param bool The <code>boolean</code> value to yield.
     */
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> always(boolean bool) {
        return (Predicate<T>) (bool ? Predicate.Always.yes() : Predicate.Always.no());
    }



    /**
     * Create a function which always throw the given exception. If the
     * exception is not a {@link RuntimeException} it will be wrapped as such.
     *
     * @param ex The exception to throw.
     */
    public static <I1, I2, O> Throw<I1, I2, O> alwaysThrow(Exception ex) { return new Throw<>(ex); }



    /**
     * Evaluates if objects are contained in the given <code>Iterable</code>.
     */
    public static <T> Predicate<T> containedIn(final Iterable<? extends T> iterable) {
        if (iterable == null) return Always.<T>no();
        if (iterable instanceof Collection) {
            final Collection<? extends T> collection = (Collection<? extends T>) iterable;
            return collection.isEmpty() ? Always.<T>no() : new Predicate<T>() {
                @Override public boolean $(T value) { return collection.contains(value); }};
        } else {
            final Elements<? extends T> elements = on(iterable);
            return new Predicate<T>() {
                @Override public boolean $(T value) { return elements.exists(is(value)); }};
        }

    }

    private Base() {}

}
