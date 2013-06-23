package no.motif.iter;

import static no.motif.Singular.none;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import no.motif.Singular;
import no.motif.option.Optional;

import org.junit.Test;

public class SimpleIteratorTest {


    @Test(expected = NoSuchElementException.class)
    public void nextThrowsNoSuchElement() {
        empty.iterator().next();
    }

    @Test
    public void nextReturnsAnElementThenThrowsNoSuchElement() {
        Iterator<String> iterator = oneElement.iterator();
        assertThat(iterator.next(), is("x"));
        try { iterator.next(); } catch (NoSuchElementException e) { return; }
        fail("Should have thrown " + NoSuchElementException.class);
    }

    @Test
    public void iteratingUsingForLoop() {
        for (Object o : empty) fail("Should not yield any object, but got " + o);
        for (String s : oneElement) {
            assertThat(s, is("x"));
            return;
        }
        fail("Did not iterate");
    }




    private final Iterable<String> oneElement = new Iterable<String>() {
        @Override
        public Iterator<String> iterator() {
            return new SimpleIterator<String>() {
                boolean returned;
                @Override
                protected Optional<String> nextIfAvailable() {
                    if (returned) return none();
                    returned = true;
                    return Singular.optional("x");
                }
            };
        }
    };


    private final Iterable<Object> empty = new Iterable<Object>() {
        @Override
        public Iterator<Object> iterator() {
            return new SimpleIterator<Object>() {
                @Override
                protected Optional<Object> nextIfAvailable() {
                    return none();
                }
            };
        }
    };

}
