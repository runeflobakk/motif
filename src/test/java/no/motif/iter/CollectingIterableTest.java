package no.motif.iter;

import static java.util.Arrays.asList;
import static no.motif.Iterate.byOrderingOf;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import no.motif.NOP;

import org.junit.Test;

public class CollectingIterableTest extends CollectingIterable<Integer> {


    @Test
    public void collectsInAnUnmodifiableList() {
        List<Integer> list = this.collect();
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
        assertThat(this.collectIn(new TreeSet<Integer>()), contains(1, 2, 3, 5, 42));
    }

    @Test
    public void collectSorted() {
        assertThat(this.sortedBy(NOP.<Integer>fn()), contains(1, 1, 2, 3, 5, 42));
        assertThat(this.sorted(byOrderingOf(Integer.class)), contains(1, 1, 2, 3, 5, 42));
    }

    @Test
    public void exerciseToString() {
        this.toString();
    }


    @Override
    public Iterator<Integer> iterator() {
        return asList(42, 1, 1, 2, 5, 3).iterator();
    }


}
