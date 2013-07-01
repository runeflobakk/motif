package no.motif;

import static no.motif.Base.toIterable;
import static no.motif.Singular.optional;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import no.motif.f.Fn;

import org.junit.Test;

public class BaseTest {

    @Test
    public void bridgeArrayYieldingFunctionToIterableYieldingFunction() {
        Fn<Object, Integer[]> oneTwoThree = new Fn<Object, Integer[]>() {
            @Override
            public Integer[] $(Object value) {
                return new Integer[] {1, 2, 3};
            }
        };
        assertThat(optional("").split(toIterable(oneTwoThree)), contains(1, 2, 3));
    }

}
