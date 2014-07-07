package no.motif.iter.boxing;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.iter.PreIndexedContentIterator;

public final class IntsIterable implements Iterable<Integer>, Serializable {

    private final int[] ints;

    public IntsIterable(int[] ints) { this.ints = ints; }

    @Override
    public final Iterator<Integer> iterator() {
        return new PreIndexedContentIterator<Integer>(ints.length) {
            @Override protected Integer elementAt(int index) { return ints[index]; }};
    }

}
