package no.motif.iter;

import static no.motif.Iterate.on;
import static no.motif.Strings.concat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AppendPrependTest {

    private final PreparedIterable<Character> hello = on("hello");

    @Test public void appendToExistingElements() {
        assertThat(hello.append(on(", world!")).reduce("", concat), is("hello, world!"));
    }

    @Test public void prependToExistingElements() {
        assertThat(hello.prepend(on("Why, ")).append('!').reduce("", concat), is("Why, hello!"));
        assertThat(hello.prepend('-').reduce("", concat), is("-hello"));
    }
}
