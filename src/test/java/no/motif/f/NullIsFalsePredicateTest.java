package no.motif.f;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NullIsFalsePredicateTest extends NullIsFalsePredicate<String> {

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
