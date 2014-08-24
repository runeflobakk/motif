package no.motif.iter;


public class CharsInStringIterator extends PreIndexedContentIterator<Character> {

    private final CharSequence string;

    public CharsInStringIterator(CharSequence string) {
        super(string.length());
        this.string = string;
    }

    @Override
    protected Character elementAt(int index) {
        return string.charAt(index);
    }

}
