package no.motif.f;

import static no.motif.Base.anyOf;
import static no.motif.Base.either;
import static no.motif.Base.is;
import static no.motif.Base.where;
import static no.motif.Strings.contains;
import static no.motif.Strings.length;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

public class DisjunctionTest {

    @Test
    public void onlyOnePredicate() {
        assertTrue(either(is(4)).$(4));
        assertFalse(either(is(4)).$(5));
    }

    @Test
    public void onlyOneMustBeTrueForTheDisjunctionToBeTrue() {
        assertFalse(either(is(1)).or(is(2)).$(3));
        assertTrue(either(is(1)).or(is(2)).$(2));
    }

    @Test
    public void disjunctionFromListOfPredicates() {
        assertTrue(anyOf(is("abc"), contains("c"), where(length, is(3))).$("cc"));
        assertFalse(anyOf(contains("c"), where(length, is(4))).$("a"));
    }

    @Test
    public void emptyDisjunctionIsFalse() {
        @SuppressWarnings("unchecked")
        Predicate<Object>[] none = (Predicate<Object>[]) Arrays.<Predicate<Object>>asList().toArray();
        assertFalse(anyOf(none).$("x"));
    }
}
