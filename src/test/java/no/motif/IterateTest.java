package no.motif;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static no.motif.Iterate.empty;
import static no.motif.Iterate.on;
import static no.motif.Singular.optional;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import no.motif.f.Fn;

import org.junit.Test;

public class IterateTest {

    @Test
    public void iteratingNullIsEmptyIterable() {
        assertThat(on((String) null), emptyIterable());
        assertThat(on((Integer[]) null), emptyIterable());
        assertThat(on((Iterable<Integer>) null), emptyIterable());
    }


    @Test
    public void emptyPredicate() {
        assertFalse(empty.$(asList(1)));
        assertTrue(empty.$(emptyList()));
    }


    @Test
    public void bridgeArrayYieldingFunctionToIterableYieldingFunction() {
        Fn<Object, Integer[]> oneTwoThree = new Fn<Object, Integer[]>() {
            @Override
            public Integer[] $(Object value) {
                return new Integer[] {1, 2, 3};
            }
        };
        assertThat(optional("").split(Iterate.toIterable(oneTwoThree)), contains(1, 2, 3));
    }

}
