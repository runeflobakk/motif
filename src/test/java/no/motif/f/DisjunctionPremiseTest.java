package no.motif.f;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static no.motif.Base.always;
import static no.motif.Base.anyOf;
import static no.motif.Base.either;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import no.motif.f.alias.Premise;

import org.junit.Test;

public class DisjunctionPremiseTest {

    @Test
    public void onlyOnePremise() {
        assertTrue(either(always(TRUE)).$());
        assertFalse(either(always(FALSE)).$());
        assertFalse(either(always((Boolean) null)).$());
    }


    @Test
    public void onlyOneMustBeTrueForTheDisjunctionToBeTrue() {
        assertFalse(either(always(FALSE)).or(always(FALSE)).$());
        assertTrue(either(always(FALSE)).or(always((Boolean) null)).or(always(TRUE)).$());
    }

    @Test
    public void disjunctionFromListOfPredicates() {
        assertTrue(anyOf(always(FALSE), always((Boolean) null), always(TRUE)).$());
        assertFalse(anyOf(always(FALSE), always((Boolean) null)).$());
    }

    @Test
    public void emptyDisjunctionIsFalse() {
        assertFalse(anyOf(new Premise[0]).$());
    }
}
