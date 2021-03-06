package no.motif.f.combine;

import static java.util.Arrays.asList;
import static no.motif.Base.equalTo;
import no.motif.Iterate;
import no.motif.f.Predicate;

/**
 * A disjunction is a composition of several predicates which evaluates
 * to true when at least <em>one</em> of the predicates yields true.
 * If all the predicates evaluates to false, the disjunction is also
 * false.
 */
public class Disjunction<T> implements Predicate<T> {

    private final no.motif.types.Appendable<Predicate<? super T>> predicates;

    @SafeVarargs
    @SuppressWarnings("varargs")
    public Disjunction(Predicate<? super T> ... predicates) {
        this(asList(predicates));
    }

    public Disjunction(Iterable<Predicate<? super T>> predicates) {
        this.predicates = Iterate.on(predicates);
    }

    @Override
    public boolean $(T value) {
        for (Predicate<? super T> p : predicates) if (p.$(value)) return true;
        return false;
    }


    public Disjunction<T> or(T other) {
        return or(equalTo(other));
    }


    public Disjunction<T> or(Predicate<? super T> otherPredicate) {
        return new Disjunction<T>(predicates.append(otherPredicate));
    }

}
