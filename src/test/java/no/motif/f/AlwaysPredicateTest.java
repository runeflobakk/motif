package no.motif.f;

import static no.motif.Base.always;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import no.motif.f.Predicate.Always;

import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.generator.ValuesOf;

@RunWith(Theories.class)
public class AlwaysPredicateTest {

    private final int randomInput = new Random().nextInt(Integer.MAX_VALUE);

    @Theory
    public void constantPredicateAlwaysYieldsSameResult(@ForAll Object anything, @ForAll @ValuesOf boolean bool) {
        assertTrue(Always.yes().$(null));
        assertTrue(randomInput + " should yield true", Always.yes().$(anything));

        assertFalse(Always.no().$(null));
        assertFalse(randomInput + " should yield false", Always.no().$(anything));

        assertThat(always(bool).$(anything), is(bool));
    }

}
