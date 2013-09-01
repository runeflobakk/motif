package no.motif.iter;

import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;
import no.motif.single.Elem;

import org.junit.Test;

public class IndexedIterableTest {

    @SuppressWarnings("unchecked")
    @Test
    public void indexesElements() {
        assertThat(on().indexed(), emptyIterable());
        assertThat(on("a", "b").indexed(), contains(Elem.of(0, "a"), Elem.of(1, "b")));
    }
}
