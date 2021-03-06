package no.motif.iter;

import static no.motif.Base.toString;
import static no.motif.Iterate.byOrderingOf;
import static no.motif.Iterate.on;
import static no.motif.Singular.none;
import static no.motif.Singular.optional;
import static no.motif.Strings.length;
import static no.motif.Strings.toInt;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import no.motif.Iterate;
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

    @Test
    public void getLastElement() {
        assertThat(on(1, 2, 3).last(), is(optional(3)));
    }

    @Test
    public void lastElementOfEmptyElementsIsNone() {
        assertThat(Iterate.none().last(), is(none()));
    }

    @Test
    public void groupingElements() {
        Map<Integer, List<String>> stringsByLength = on("ab", "bc", "d").groupBy(length);
        assertThat(stringsByLength, hasEntry(is(1), contains("d")));
        assertThat(stringsByLength, hasEntry(is(2), contains("ab", "bc")));
        assertThat(stringsByLength.size(), is(2));

        assertThat(Iterate.none().groupBy(toString).size(), is(0));
    }

    @Test
    public void createMapWhereEachElementsCorrespondsToAUniqueKey() {
        Map<Integer, String> map = on("1", "34").mapBy(toInt);
        assertThat(map, hasEntry(1, "1"));
        assertThat(map, hasEntry(34, "34"));
    }

    @Test(expected = IllegalStateException.class)
    public void createMapWhereMultipleElementsResolvesToSameKeyIsAnError() {
        on("1", "34", "1").mapBy(toInt);
    }

}
