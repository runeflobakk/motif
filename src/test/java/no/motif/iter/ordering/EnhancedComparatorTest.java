package no.motif.iter.ordering;

import static no.motif.Base.equalTo;
import static no.motif.Ints.even;
import static no.motif.Ints.odd;
import static no.motif.Iterate.by;
import static no.motif.Iterate.byOrderingOf;
import static no.motif.Iterate.on;
import static no.motif.Strings.blank;
import static no.motif.Strings.length;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import no.motif.Longs;
import no.motif.f.Fn;

import org.junit.Test;

public class EnhancedComparatorTest {

    @Test
    public void descendingOrder() {
        assertThat(on("A", "B", "C").sorted(byOrderingOf(String.class).reversed()), contains("C", "B", "A"));
    }

    @Test
    public void placeNullsFirst() {
        assertThat(on("B", "A", null).sorted(byOrderingOf(String.class).nullsFirst()), contains(null, "A", "B"));
    }

    @Test
    public void placeNullsLast() {
        assertThat(on("B", null, "A").sorted(byOrderingOf(String.class).nullsLast()), contains("A", "B", null));
    }

    @Test
    public void priorityIsEvaluatedOnActualElements() {
        assertThat(on("22", null, "1").sorted(by(length).nullsLast()), contains("1", "22", null));
    }

    @Test
    public void nullSafety() {
        Fn<String, Integer> notNullSafeLength = new Fn<String, Integer>() {
            @Override public Integer $(String s) { return s.length(); }};

        assertThat(on("1", null, null, "22", null).sorted(by(notNullSafeLength).nullsFirst()), contains(null, null, null, "1", "22"));
        assertThat(on("1", null, null, "22", null).sorted(by(notNullSafeLength).nullsLast()), contains("1", "22", null, null, null));
    }

    @Test
    public void priorityByPredicate() {
        assertThat(on("22", "1", "awesome").sorted(by(length).first(equalTo("awesome"))), contains("awesome", "1", "22"));
        assertThat(on("22", "", "1").sorted(by(length).last(blank)), contains("1", "22", ""));
    }

    @Test
    public void severalMatchedByPriorityIsOrdered() {
        assertThat(on(4, 2, 3, 1).sorted(byOrderingOf(Integer.class).last(even)), contains(1, 3, 2, 4));
        assertThat(on(4, 2, 3, 1).sorted(byOrderingOf(Integer.class).first(odd)), contains(1, 3, 2, 4));
        assertThat(on(4L, 2L, 3L, 1L).sorted(byOrderingOf(Long.class).last(Longs.even)), contains(1L, 3L, 2L, 4L));
        assertThat(on(4L, 2L, 3L, 1L).sorted(byOrderingOf(Long.class).first(Longs.odd)), contains(1L, 3L, 2L, 4L));
    }
}
