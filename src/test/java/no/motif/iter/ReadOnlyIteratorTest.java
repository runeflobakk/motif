package no.motif.iter;

import org.junit.Test;

public class ReadOnlyIteratorTest {

    @Test(expected = UnsupportedOperationException.class)
    public void removingElementsIsNotSupported() {
        new ReadOnlyIterator<String>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public String next() {
                return "";
            }
        }.remove();

    }
}
