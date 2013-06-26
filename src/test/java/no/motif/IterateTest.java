package no.motif;

import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class IterateTest {

    @Test
    public void iteratingNullIsEmptyIterable() {
        assertThat(on((String) null), emptyIterable());
        assertThat(on((Integer[]) null), emptyIterable());
        assertThat(on((Iterable<Integer>) null), emptyIterable());
    }
}
