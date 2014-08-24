package no.motif.f.combine;

import static java.util.Arrays.asList;
import no.motif.Iterate;
import no.motif.f.Fn0;

/**
 * A composition of several {@link Fn0 Fn0&lt;Boolean&gt;s} which must all evaluate
 * to <code>true</code> for the conjunction to yield <code>true</code>,
 * otherwise it yields <code>false</code>.
 */
public class ConjunctionPremise implements Fn0<Boolean> {

    private final no.motif.types.Appendable<Fn0<Boolean>> premises;

    @SafeVarargs
    @SuppressWarnings("varargs")
    public ConjunctionPremise(Fn0<Boolean> ... premises) {
        this(asList(premises));
    }

    public ConjunctionPremise(Iterable<Fn0<Boolean>> premises) {
        this.premises = Iterate.on(premises);
    }

    @Override
    public Boolean $() {
        for (Fn0<Boolean> p : premises) if (p.$() != Boolean.TRUE) return false;
        return true;
    }

    /**
     *
     * @param anotherPremise must yield <code>true</code>
     *                       for the entire premise to be true.
     * @return the new {@link ConjunctionPremise}
     */
    public ConjunctionPremise and(Fn0<Boolean> anotherPremise) {
        return new ConjunctionPremise(premises.append(anotherPremise));
    }

}
