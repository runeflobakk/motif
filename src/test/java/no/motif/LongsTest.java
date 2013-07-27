package no.motif;

import static no.motif.Longs.longValue;
import static no.motif.Longs.rounded;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LongsTest {

    @Test public void longValueIsNullsafe() {
        assertThat(longValue.$(null), is(0L));
    }

    @Test public void longValue() {
        assertThat(longValue.$(Double.MAX_VALUE), is(Long.MAX_VALUE));
    }

    @Test public void rounding() {
        assertThat(rounded.$(3.14), is(3L));
        assertThat(rounded.$(4.5), is(5L));
    }
}
