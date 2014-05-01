package no.motif;

import static no.motif.IO.writeTo;
import static no.motif.Iterate.on;
import static no.motif.Singular.the;
import static no.motif.Strings.append;
import static no.motif.Strings.bytes;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IOTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void streamThrowingException() {
        OutputStream fubar = new OutputStream() {
            @Override public void write(int b) throws IOException { throw new IOException("I am error"); }};

        expectedException.expectMessage("I am error");
        writeTo(fubar).with((byte) 65);
    }

    @Test
    public void writeStringToOutputStream() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        the("abc").split(bytes).each(writeTo(out));
        assertThat(out.toString(), is("abc"));
    }

    @Test
    public void writeSeveralStringsToOutputStream() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        on("Line 1", "Line 2").map(append("\n")).flatMap(bytes).each(writeTo(out));
        assertThat(out.toString(), is("Line 1\nLine 2\n"));
    }
}
