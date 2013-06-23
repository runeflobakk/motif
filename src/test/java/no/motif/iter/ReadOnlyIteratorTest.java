package no.motif.iter;

import static org.mockito.Mockito.mock;
import org.junit.Test;

public class ReadOnlyIteratorTest {

    @Test(expected = UnsupportedOperationException.class)
    public void removingElementsIsNotSupportedAndNotOverrideable() {
        mock(ReadOnlyIterator.class).remove();
    }

}
