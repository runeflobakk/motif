package no.motif.iter.boxing;

import java.io.Serializable;
import java.util.Iterator;

import no.motif.iter.PreIndexedContentIterator;

public final class BytesIterable implements Iterable<Byte>, Serializable {

    private final byte[] bytes;

    public BytesIterable(byte[] bytes) { this.bytes = bytes; }

    @Override
    public final Iterator<Byte> iterator() {
        return new PreIndexedContentIterator<Byte>(bytes.length) {
            @Override protected Byte elementAt(int index) { return bytes[index]; }};
    }

}
