package no.motif.f;

import static no.motif.Base.alwaysThrow;
import no.motif.f.base.Throw;

import org.junit.Test;

public class ThrowTest {

    Throw<String, String, String> chokes = alwaysThrow(new StringIndexOutOfBoundsException());

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void throwsExceptionForFn() {
        chokes.$("anything");
    }


    @Test(expected = StringIndexOutOfBoundsException.class)
    public void throwsExceptionForFn2() {
        chokes.$("anything", "anything");
    }


    @Test(expected = StringIndexOutOfBoundsException.class)
    public void throwsExceptionForFn0() {
        chokes.$();
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void throwsExceptionForDo() {
        chokes.with("anything");
    }
}
