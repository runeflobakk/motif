package no.motif.f.combine;

import static java.util.Arrays.asList;
import no.motif.Iterate;
import no.motif.f.Fn0;
import no.motif.types.Appendable;

/**
 * A disjunction is a composition of several {@link Fn0 Fn0&lt;Boolean&gt;s}
 * which evaluates to <code>true</code> only when at least <em>one</em> of the
 * premises yields true.
 * If none of the <code>Fn0&lt;Boolean&gt;s</code> yields <code>true</code>,
 * the disjunction yields <code>false</code>.
 */
public class DisjunctionPremise implements Fn0<Boolean> {

    private final Appendable<Fn0<Boolean>> premises;

    @SafeVarargs
    @SuppressWarnings("varargs")
    public DisjunctionPremise(Fn0<Boolean> ... premises) {
        this(asList(premises));
    }

    public DisjunctionPremise(Iterable<Fn0<Boolean>> premises) {
        this.premises = Iterate.on(premises);
    }

    @Override
    public Boolean $() {
        for (Fn0<Boolean> p : premises) if (p.$() == Boolean.TRUE) return true;
        return false;
    }

    public DisjunctionPremise or(Fn0<Boolean> otherPremise) {
        return new DisjunctionPremise(premises.append(otherPremise));
    }

}
