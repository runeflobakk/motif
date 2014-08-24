package no.motif.f;

import static no.motif.Exceptions.cause;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;

public class ApplyTest {

    @Test
    public void convertFnToDo() {
        Exception exception = mock(Exception.class);
        Apply.justSideEffectOf(cause).with(exception);
        verify(exception, times(1)).getCause();
        verifyNoMoreInteractions(exception);
    }
}
