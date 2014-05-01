package no.motif.iter.boxing;

import java.util.Iterator;

import no.motif.iter.PreIndexedContentIterator;

public final class CharsIterable implements Iterable<Character> {

    private final char[] chars;

    public CharsIterable(char[] chars) { this.chars = chars; }

    @Override
    public final Iterator<Character> iterator() {
        return new PreIndexedContentIterator<Character>(chars.length) {
            @Override protected Character elementAt(int index) { return chars[index]; }};
    }

}
