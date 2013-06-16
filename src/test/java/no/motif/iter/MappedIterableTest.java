package no.motif.iter;

import static no.motif.Functions.toString;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class MappedIterableTest {

    @Test
    public void mapAnEmptyIterable() {
        assertThat(on().map(toString), emptyIterable());
    }

    @Test
    public void mapTheValuesOfAnIterable() {
        assertThat(on(1, 2).map(toString), contains("1", "2"));
    }

}
