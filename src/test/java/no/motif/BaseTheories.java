package no.motif;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static no.motif.Base.containedIn;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeThat;

import java.util.List;
import java.util.Objects;

import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;

@RunWith(Theories.class)
public class BaseTheories {

    @Theory
    public void hashCode(@ForAll Object anyObject) {
        assertThat(Base.hashCode.$(anyObject), is(Objects.hashCode(anyObject)));
    }

    @Theory
    public void containedInNullOrEmptyCollectionIsAlwaysFalse(@ForAll Object anyObject) {
        assertFalse(containedIn(null).$(anyObject));
        assertFalse(containedIn(emptyList()).$(anyObject));
    }

    @Theory
    public void containedInANonEmptyCollection(
            @ForAll (sampleSize=20) Object inCollection,
            @ForAll (sampleSize=20) Object anyOtherObject) {

        assumeThat(inCollection, not(anyOtherObject));
        List<?> list = asList(inCollection);
        assertTrue(containedIn(list).$(inCollection));
        assertFalse(containedIn(list).$(anyOtherObject));
    }
}
