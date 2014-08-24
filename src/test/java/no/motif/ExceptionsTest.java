package no.motif;

import static no.motif.Exceptions.asRuntimeException;
import static no.motif.Exceptions.cause;
import static no.motif.Exceptions.message;
import static no.motif.Iterate.last;
import static no.motif.Singular.optional;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class ExceptionsTest {

    @Test
    public void runtimeExceptionsAreReturnedAsIs() {
        Exception exception = new IllegalArgumentException("error");
        RuntimeException runtimeException = asRuntimeException(exception);
        assertThat(runtimeException, sameInstance(exception));
        assertThat(runtimeException.getMessage(), is("error"));
    }

    @Test
    public void checkedExceptionsAreWrappedInARuntimeException() {
        IOException ex = new IOException("Some IO failed epicly");
        RuntimeException runtimeException = asRuntimeException(ex);
        assertThat(ex, sameInstance(runtimeException.getCause()));
        assertThat(runtimeException.getMessage(), containsString(ex.getMessage()));
    }

    @Test
    public void runtimeExceptionHasSameStacktraceAsWrappedException() {
        IOException ex = new IOException("IO fail");
        RuntimeException runtimeException = asRuntimeException(ex);
        assertTrue(Arrays.equals(runtimeException.getStackTrace(), ex.getStackTrace()));
    }

    @Test
    public void getTheRootMessageOfAnException() {
        Exception e = new RuntimeException(new IllegalArgumentException(
                new UnsupportedOperationException("not supported")));
        assertThat(optional(e).map(last(cause)).map(message).get(), is("not supported"));
        assertThat(optional(new RuntimeException("fail")).map(last(cause)).map(message).get(), is("fail"));
        assertThat(optional(new Exception()).map(last(cause)).map(message), is(Singular.<String>none()));
    }
}
