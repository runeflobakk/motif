package no.motif.iter;

import static no.motif.Iterate.none;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.NoSuchElementException;

import no.motif.Singular;

import org.junit.Test;

public class HeadTailTest {

    @Test
    public void headOfEmptyElementsIsNone() {
        assertThat(none().head(), is(Singular.none()));
    }

    @Test
    public void headOfElements() {
        assertThat(on("abc").head(), contains('a'));
    }

    @Test
    public void tailOfElements() {
        assertThat(on("abc").tail(), contains('b', 'c'));
    }

    @Test(expected = NoSuchElementException.class)
    public void tailOfEmptyElementsThrowsException() {
        none().tail();
    }
}
