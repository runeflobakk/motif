package no.motif.iter;

import static no.motif.Iterate.byOrderingOf;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.TreeSet;

import no.motif.NOP;
import no.motif.types.YieldsJavaCollection;

import org.junit.Test;

public class CollectingIterableTest {

    private YieldsJavaCollection<Integer> ints = on(42, 1, 1, 2, 5, 3);


    @Test
    public void collectsInAnUnmodifiableList() {
        List<Integer> list = ints.collect();
        assertThat(list, contains(42, 1, 1, 2, 5, 3));
        try {
            list.add(8);
        } catch (UnsupportedOperationException e) {
            return;
        }
        fail("Should throw exception when trying to modify the list");
    }

    @Test
    public void collectInCustomCollection() {
        assertThat(ints.collectIn(new TreeSet<Integer>()), contains(1, 2, 3, 5, 42));
    }

    @Test
    public void collectSorted() {
        assertThat(ints.sortedBy(NOP.<Integer>fn()), contains(1, 1, 2, 3, 5, 42));
        assertThat(ints.sorted(byOrderingOf(Integer.class)), contains(1, 1, 2, 3, 5, 42));
    }

    @Test
    public void exerciseToString() {
        ints.toString();
    }

}
