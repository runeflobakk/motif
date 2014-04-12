package no.motif;

import static no.motif.Iterate.on;
import static no.motif.Longs.dividedBy;
import static no.motif.Longs.longValue;
import static no.motif.Longs.multipliedBy;
import static no.motif.Longs.rounded;
import static no.motif.Longs.subtract;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LongsTest {

    @Test public void longValueIsNullsafe() {
        assertThat(longValue.$(null), nullValue());
    }

    @Test public void longValue() {
        assertThat(longValue.$(Double.MAX_VALUE), is(Long.MAX_VALUE));
    }

    @Test public void rounding() {
        assertThat(rounded.$(3.14), is(3L));
        assertThat(rounded.$(4.5), is(5L));
    }

    @Test public void subtractValue() {
        assertThat(on(4, 3).map(subtract(2)), contains(2L, 1L));
        assertThat(on(4, 3).map(subtract(-2)), contains(6L, 5L));
    }

    @Test public void multiplyValue() {
        assertThat(on(4, 3).map(multipliedBy(3)), contains(12L, 9L));
    }

    @Test public void divideValue() {
        assertThat(on(20, 13).map(dividedBy(4)), contains(5L, 3L));
    }
}
