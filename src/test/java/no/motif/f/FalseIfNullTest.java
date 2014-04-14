package no.motif.f;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import no.motif.f.base.FalseIfNull;

import org.junit.Test;

public class FalseIfNullTest extends FalseIfNull<String> {

    @Test
    public void doesNotCallNullsafeMethodIfValueIsNull() {
        assertFalse(this.$(null));
    }

    @Test
    public void delegatesToNullsafeMethodForNonNullValues() {
        assertTrue(this.$(""));
    }

    @Override
    protected boolean orElse(String value) {
        return true;
    }

}
