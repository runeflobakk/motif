package no.motif.iter;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import no.motif.iter.boxing.BytesIterable;
import no.motif.iter.boxing.CharsIterable;
import no.motif.iter.boxing.IntsIterable;

import org.junit.Test;

public class PreIndexedContentIteratorTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwsExceptionIfEndIndexLessThanStartIndex() {
        new PreIndexedContentIterator<Character>(1, 0) {
            @Override protected Character elementAt(int index) { return null; }};
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void throwsIndexOutOfBoundsIfCallingNextWhenNoElementsAreLeft() {
        new IntsIterable(new int[0]).iterator().next();
    }

    @Test
    public void yieldsEachElementOfAnIntArray() {
        Iterator<Integer> iterator = new IntsIterable(new int[]{1, 2}).iterator();
        assertThat(iterator.next(), is(1));
        assertThat(iterator.next(), is(2));
        assertFalse(iterator.hasNext());
    }

    @Test
    public void iterateEmptyArray() {
        assertThat(new BytesIterable(new byte[0]), emptyIterable());
    }

    @Test
    public void iterateCharArray() {
        assertThat(new CharsIterable(new char[] {'a'}), contains('a'));
    }

}
