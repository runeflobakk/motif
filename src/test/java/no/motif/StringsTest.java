package no.motif;

import static no.motif.Strings.blank;
import static no.motif.Strings.contains;
import static no.motif.Strings.length;
import static no.motif.Strings.lowerCased;
import static no.motif.Strings.numeric;
import static no.motif.Strings.toDouble;
import static no.motif.Strings.toInt;
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

    @Test
    public void blank() {
        assertThat(blank.$("x"), is(false));
        assertThat(blank.$(""), is(true));
        assertThat(blank.$(" "), is(true));
        assertThat(blank.$("\n \t "), is(true));
        assertThat(blank.$(null), is(true));
    }

    @Test
    public void numeric() {
        assertThat(numeric.$("1"), is(true));
        assertThat(numeric.$("1234567890"), is(true));

        assertThat(numeric.$("123x"), is(false));
        assertThat(numeric.$(""), is(false));
        assertThat(numeric.$(null), is(false));
    }

    @Test
    public void length() {
        assertThat(length.$(""), is(0));
        assertThat(length.$("xyz"), is(3));
        assertThat(length.$(null), is(0));
    }

    @Test
    public void convertToInt() {
        assertThat(toInt.$("42"), is(42));
        assertThat(toInt.$("-42"), is(-42));
        assertThat(toInt.$(null), is(0));
    }

    @Test
    public void convertToDouble() {
        assertThat(toDouble.$("42"), is(42.0));
        assertThat(toDouble.$("-3.14"), is(-3.14));
        assertThat(toDouble.$(null), is(0.0));
    }

    @Test
    public void containsSequenceOfChars() {
        assertThat(contains("").$(null), is(false));
        assertThat(contains("").$(""), is(true));
        assertThat(contains("").$("a"), is(true));
        assertThat(contains("b").$("a"), is(false));
        assertThat(contains("b").$("abc"), is(true));
    }


}
