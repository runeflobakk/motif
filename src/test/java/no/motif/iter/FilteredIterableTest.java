package no.motif.iter;

import static no.motif.Base.either;
import static no.motif.Base.equalTo;
import static no.motif.Base.is;
import static no.motif.Base.isNull;
import static no.motif.Iterate.none;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class FilteredIterableTest {

    @Test
    public void filterAnEmptyIterable() {
        assertThat(none().filter(equalTo(null)), emptyIterable());
    }

    @Test
    public void filterOnlyMatchesLastElement() {
        assertThat(on("a", "b").filter(equalTo("b")), contains("b"));
    }

    @Test
    public void filterOnlyMatchesFirstElement() {
        assertThat(on("a", "b").filter(equalTo("a")), contains("a"));
    }

    @Test
    public void filterMatchesNoneOfTheElements() {
        assertThat(on("a", "b", "c").filter(equalTo("d")), emptyIterable());
    }

    @Test
    public void treatsNullsAsValidElements() {
        assertThat(on("a", null, "b", null).filter(either(is("a")).or(isNull)), contains("a", null, null));
    }

}
