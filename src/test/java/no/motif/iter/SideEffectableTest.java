package no.motif.iter;

import static no.motif.IO.print;
import static no.motif.IO.println;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.io.PrintStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SideEffectableTest {

    @Mock
    private PrintStream out;

    @Test
    public void doSideeffectOperation() {

        on("a", "b", "c").each(print(out));
        on("1", "2").each(println(out));

        ArgumentCaptor<String> printCaptor = ArgumentCaptor.forClass(String.class);
        verify(out, times(3)).print((Object) printCaptor.capture());
        assertThat(printCaptor.getAllValues(), contains("a", "b", "c"));

        ArgumentCaptor<String> printlnCaptor = ArgumentCaptor.forClass(String.class);
        verify(out, times(2)).println((Object) printlnCaptor.capture());
        assertThat(printlnCaptor.getAllValues(), contains("1", "2"));

        verifyNoMoreInteractions(out);
    }
}
