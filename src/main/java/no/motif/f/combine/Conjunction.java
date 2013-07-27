package no.motif.f.combine;

import static java.util.Arrays.asList;
import no.motif.Iterate;
import no.motif.f.Predicate;
import no.motif.iter.PreparedIterable;

/**
 * A conjunction is a composition of several predicates which must all evaluate
 * to true for the conjunction to be true, otherwise it is false.
 */
public class Conjunction<T> implements Predicate<T> {

    private final PreparedIterable<Predicate<? super T>> predicates;

    @SafeVarargs
    @SuppressWarnings("varargs")
    public Conjunction(Predicate<? super T> ... predicates) {
        this(asList(predicates));
    }

    public Conjunction(Iterable<Predicate<? super T>> predicates) {
        this.predicates = Iterate.on(predicates);
    }

    @Override
    public boolean $(T value) {
        for (Predicate<? super T> p : predicates) if (!p.$(value)) return false;
        return true;
    }

    /**
     *
     * @param anotherPredicate must be <code>true</code>
     *                         for the entire predicate to be true.
     * @return the new predicate
     */
    public Conjunction<T> and(Predicate<? super T> anotherPredicate) {
        return new Conjunction<>(predicates.append(anotherPredicate));
    }

}
