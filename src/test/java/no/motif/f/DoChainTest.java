package no.motif.f;

import static no.motif.Base.first;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DoChainTest {

    private boolean firstExecuted;
    private boolean secondExecuted;

    private Do<String> firstAction =
            new Do<String>() { @Override public void with(String value) { firstExecuted = true; }};
    private Do<CharSequence> secondAction =
            new Do<CharSequence>() { @Override public void with(CharSequence value) { secondExecuted = true; }};

    @Test
    public void executesOneAction() {
        first(firstAction).with("anything");
        assertTrue(firstExecuted);
        assertFalse(secondExecuted);
    }

    @Test
    public void executesTwoActionsInSequence() {
        first(firstAction).then(secondAction).with("anything");
        assertTrue(firstExecuted);
        assertTrue(secondExecuted);
    }
}
