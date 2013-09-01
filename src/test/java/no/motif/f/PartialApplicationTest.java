package no.motif.f;

import static no.motif.Ints.multiply;
import static no.motif.Strings.length;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PartialApplicationTest {

    @Test
    public void partiallyApplyAFn2() {
        Fn<Number, Integer> doubler = Apply.partially(multiply).of(2);
        assertThat(doubler.$(4), is(8));
    }

    @Test
    public void partiallyApplyAFn() {
        Fn0<Integer> lengthOfAbc = Apply.partially(length).of("abc");
        assertThat(lengthOfAbc.$(), is(3));
    }
}
