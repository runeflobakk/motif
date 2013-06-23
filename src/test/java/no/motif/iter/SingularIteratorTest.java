package no.motif.iter;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

public class SingularIteratorTest {

    private final Iterable<Integer> anInt = new Iterable<Integer>() {
        @Override
        public Iterator<Integer> iterator() {
            return new SingularIterator<Integer>(42);
        }
    };

    @Test
    public void yieldsASingleValue() {
        for (int i : anInt) {
            assertThat(i, is(42));
            return;
        }
        fail("Did not iterate");
    }

    @Test
    public void throwsExceptionIfNextIsCalledTwice() {
        Iterator<Integer> iterator = anInt.iterator();
        iterator.next();
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            return;
        }
        fail("Should have thrown " + NoSuchElementException.class);
    }
}
