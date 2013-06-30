package no.motif.f;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import no.motif.f.Predicate.Always;

import org.junit.Test;

public class AlwaysPredicateTest {

    private final int randomInput = new Random().nextInt(Integer.MAX_VALUE);

    @Test
    public void alwaysYieldsTrue() {
        assertTrue(Always.yes().$(null));
        assertTrue(randomInput + " should yield true", Always.yes().$(randomInput));
    }

    @Test
    public void alwaysYieldsFalse() {
        assertFalse(Always.no().$(null));
        assertFalse(randomInput + " should yield false", Always.no().$(randomInput));
    }
}
