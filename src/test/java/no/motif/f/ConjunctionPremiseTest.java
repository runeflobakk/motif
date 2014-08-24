package no.motif.f;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static no.motif.Base.allOf;
import static no.motif.Base.always;
import static no.motif.Base.both;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import no.motif.f.alias.Premise;

import org.junit.Test;

public class ConjunctionPremiseTest {

    @Test
    public void onlyOnePredicate() {
        assertFalse(both(always(FALSE)).$());
        assertFalse(both(always((Boolean) null)).$());
        assertTrue(both(always(TRUE)).$());
    }

    @Test
    public void allMustBeTrueForTheConjunctionToBeTrue() {
        assertFalse(both(always(TRUE)).and(always(FALSE)).$());
        assertFalse(both(always(TRUE)).and(always((Boolean) null)).$());
        assertTrue(both(always(TRUE)).and(always(TRUE)).$());
    }

    @Test
    public void conjunctionFromListOfPremises() {
        assertTrue(allOf(always(TRUE), always(TRUE)).$());
        assertFalse(allOf(always(TRUE), always(FALSE)).$());
    }

    @Test
    public void emptyConjunctionIsTrue() {
        assertTrue(allOf(new Premise[0]).$());
    }
}
