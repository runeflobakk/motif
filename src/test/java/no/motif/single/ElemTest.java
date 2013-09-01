package no.motif.single;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.Test;

public class ElemTest {

    @Test
    public void hasNiceToString() {
        assertThat(Elem.of(42, "the answer").toString(), is("[42: the answer]"));
    }

    @Test
    public void adheresToEqualsHashCodeContract() {
        EqualsVerifier.forClass(Elem.class).verify();
    }
}
