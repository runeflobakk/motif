package no.motif;

import static no.motif.Enums.toSameName;
import static no.motif.Singular.the;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class EnumsTest {

    enum A { X }
    enum B { X }

    @Test
    public void mapBetweenUnrelatedEnumsByName() {
        assertThat(the(A.X).map(toSameName(B.class)), contains(B.X));
    }
}
