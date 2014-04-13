package no.motif;

import static no.motif.Base.always;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.TimeZone;

import org.junit.Test;

public class ImplicitsTest {

    @Test
    public void defaultTimeZone() {
        assertThat(Implicits.getTimeZone(), is(TimeZone.getDefault()));
    }

    @Test
    public void setImplicitTimeZone() {
        TimeZone pst = TimeZone.getTimeZone("PST");
        Implicits.setTimeZone(always(pst));
        assertThat(Implicits.getTimeZone(), is(pst));
        Implicits.setDefaultTimeZone();
    }
}
