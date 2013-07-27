package no.motif.f;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import no.motif.f.base.FalseIfNullOrElse;

import org.junit.Test;

public class FalseIfNullOrElseTest extends FalseIfNullOrElse<String> {

    @Test
    public void doesNotCallNullsafeMethodIfValueIsNull() {
        assertFalse(this.$(null));
    }

    @Test
    public void delegatesToNullsafeMethodForNonNullValues() {
        assertTrue(this.$(""));
    }

    @Override
    protected boolean $nullsafe(String value) {
        return true;
    }

}
