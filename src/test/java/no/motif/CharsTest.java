package no.motif;

import static no.motif.Chars.letter;
import static no.motif.Chars.letterOrDigit;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CharsTest {

    @Test
    public void isLetter() {
        assertFalse(letter.$(null));
        assertFalse(letter.$('9'));
        assertTrue(letter.$('a'));
        assertTrue(letter.$('ö'));
    }

    @Test
    public void isLetterOrDigit() {
        assertFalse(letterOrDigit.$(null));
        assertTrue(letterOrDigit.$('9'));
        assertTrue(letterOrDigit.$('a'));
        assertTrue(letterOrDigit.$('ö'));

    }
}
