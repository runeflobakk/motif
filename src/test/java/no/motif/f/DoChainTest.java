package no.motif.f;

import static no.motif.Base.first;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DoChainTest {

    private boolean firstExecuted;
    private boolean secondExecuted;

    private Do<String> firstAction =
            new Do<String>() { @Override public void $(String value) { firstExecuted = true; }};
    private Do<CharSequence> secondAction =
            new Do<CharSequence>() { @Override public void $(CharSequence value) { secondExecuted = true; }};

    @Test
    public void executesOneAction() {
        first(firstAction).$("anything");
        assertTrue(firstExecuted);
        assertFalse(secondExecuted);
    }

    @Test
    public void executesTwoActionsInSequence() {
        first(firstAction).then(secondAction).$("anything");
        assertTrue(firstExecuted);
        assertTrue(secondExecuted);
    }
}
