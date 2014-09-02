package no.motif;

import static no.motif.Enums.name;
import static no.motif.Enums.to;
import static no.motif.Enums.toSameName;
import static no.motif.Iterate.on;
import static no.motif.Singular.the;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import org.junit.Test;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;

@RunWith(Theories.class)
public class EnumsTest {

    enum A { X, A, B, C, D, E, F, DEFAULT }
    enum B { X }

    @Test
    public void mapBetweenUnrelatedEnumsByName() {
        assertThat(the(A.X).map(toSameName(B.class)), contains(B.X));
    }


    @Theory
    public void resolveGivenNameAsEnum(@ForAll A a) {
        assumeThat(a, not(A.DEFAULT));
        assertThat(the(a.name()).map(to(A.class).or(A.DEFAULT)), contains(a));
    }


    @Theory
    public void resolveToFallbackValue(@ForAll String unknownName) {
        assumeThat(unknownName, not(isIn(on(A.values()).map(name).collect())));
        assertThat(the(unknownName).map(to(A.class).or(A.DEFAULT)), contains(A.DEFAULT));
    }
}
