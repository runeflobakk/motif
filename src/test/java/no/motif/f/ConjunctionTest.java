package no.motif.f;

import static no.motif.Base.allOf;
import static no.motif.Base.both;
import static no.motif.Base.is;
import static no.motif.Base.where;
import static no.motif.Strings.contains;
import static no.motif.Strings.length;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ConjunctionTest {

    @Test
    public void onlyOnePredicate() {
        assertFalse(both(is("x")).$("y"));
        assertTrue(both(is("x")).$("x"));
    }

    @Test
    public void allMustBeTrueForTheConjunctionToBeTrue() {
        assertFalse(both(is("x")).and(is("y")).$("x"));
        assertTrue(both(is("abc")).and(contains("b")).$("abc"));
    }

    @Test
    public void conjunctionFromListOfPredicates() {
        assertTrue(allOf(is("abc"), contains("c"), where(length, is(3))).$("abc"));
        assertFalse(allOf(contains("c"), where(length, is(4))).$("abc"));
    }

    @Test
    public void emptyConjunctionIsTrue() {
        assertTrue(allOf().$("x"));
    }
}
