package no.motif;

import static no.motif.Exceptions.asRuntimeException;
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
}
