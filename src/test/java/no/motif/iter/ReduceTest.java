package no.motif.iter;

import static no.motif.Iterate.on;
import static no.motif.Ints.multiply;
import static no.motif.Ints.sum;
import static no.motif.Strings.concat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ReduceTest {

    @Test
    public void sumIntegers() {
        assertThat(on(1, 2, 3, 4).reduce(0, sum), is(10));
    }

    @Test
    public void multiplyIntegers() {
        assertThat(on(2, 5, 3, -1).reduce(1, multiply), is(-30));
    }

    @Test
    public void concatenateStrings() {
        assertThat(on("ab", "cd", "e").reduce("", concat), is("abcde"));
    }

    @Test
    public void concatenateCharsToString() {
        assertThat(on('a', 'b', 'c').reduce("", concat), is("abc"));
    }

    @Test
    public void concatenatingNoElementsYieldsTheUnitValue() {
        assertThat(on().reduce("", concat), is(""));
    }

}
