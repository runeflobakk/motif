package no.motif;

import static no.motif.Strings.lowerCased;
import static no.motif.Strings.upperCased;
import static no.motif.Strings.trimmed;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class StringsTest {

    @Test
    public void toUpperCase() {
        assertThat(upperCased.$("abc"), is("ABC"));
    }

    @Test
    public void toLowerCase() {
        assertThat(lowerCased.$("ABC"), is("abc"));
    }

    @Test
    public void trimmed() {
        assertThat(trimmed.$("  x y  "), is("x y"));
    }

}
