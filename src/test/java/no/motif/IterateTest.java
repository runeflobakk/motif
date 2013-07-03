package no.motif;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static no.motif.Iterate.empty;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
}
