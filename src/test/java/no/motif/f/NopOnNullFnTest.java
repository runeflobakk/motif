package no.motif.f;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Test;

public class NopOnNullFnTest {

    @Test
    public void doesNotInvokeImplementorOnNull() {
        Fn<String, String> fn = new NopOnNullFn<String, String>() {
            @Override
            protected String $nullsafe(String value) {
                fail("should not invoke this method");
                return "";
            }
        };
        assertThat(fn.$(null), nullValue());
    }

    @Test
    public void passesFunctionArgToImplemtor() {
        Fn<String, Integer> fn = new NopOnNullFn<String, Integer>() {
            @Override
            protected Integer $nullsafe(String value) {
                return value.length();
            }
        };

        assertThat(fn.$("xy"), is(2));

    }
}
