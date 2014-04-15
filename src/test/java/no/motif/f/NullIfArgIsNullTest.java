package no.motif.f;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import no.motif.f.base.NullIfArgIsNull;
import no.motif.f.base.NullIfEitherArgIsNull;

import org.junit.Test;

public class NullIfArgIsNullTest {

    @Test
    public void doesNotInvokeImplementorOnNull() {
        Fn<String, String> fn = new NullIfArgIsNull<String, String>() {
            @Override
            protected String orElse(String value) {
                fail("should not invoke this method");
                return "";
            }
        };
        assertThat(fn.$(null), nullValue());
    }

    @Test
    public void passesFunctionArgToImplemtor() {
        Fn<String, Integer> fn = new NullIfArgIsNull<String, Integer>() {
            @Override
            protected Integer orElse(String value) {
                return value.length();
            }
        };

        assertThat(fn.$("xy"), is(2));
    }

    @Test
    public void doesNotInvokeFn2ImplementorOnNullArgs() {
        Fn2<Object, Object, Integer> fn2 = new NullIfEitherArgIsNull<Object, Object, Integer>() {
            @Override protected Integer orElse(Object first, Object second) {
                fail("should not be called");
                return null;
            }};

        assertThat(fn2.$(null, "a"), nullValue());
        assertThat(fn2.$("a", null), nullValue());
        assertThat(fn2.$(null, null), nullValue());
    }
}
