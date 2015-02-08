package no.motif;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
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
        ImplicitsTestHarness.setTimeZone(pst);
        assertThat(Implicits.getTimeZone(), is(pst));
        ImplicitsTestHarness.setTimeZone(null);
    }

    @Test
    public void defaultTimeMillis() {
        long now = System.currentTimeMillis();
        assertThat(Implicits.getTimeMillis(), greaterThanOrEqualTo(now));
    }

    @Test
    public void overrideSystemTime() {
        ImplicitsTestHarness.setTimeMillis(1000L);
        assertThat(Implicits.getTimeMillis(), is(1000L));
        ImplicitsTestHarness.setTimeMillis(null);
    }

}
