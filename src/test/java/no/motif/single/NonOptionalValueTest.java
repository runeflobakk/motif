package no.motif.single;

import static no.motif.Base.greaterThan;
import static no.motif.Singular.the;
import static no.motif.Strings.length;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import no.motif.Singular;

import org.junit.Test;

public class NonOptionalValueTest {
    @Test
    public void nullIsOk() {
        assertThat(the(null).get(), nullValue());
    }

    @Test
    public void mappingFollowsSameSemanticsAsOptional() {
        A<String> nullstring = the((String )null);
        assertThat(nullstring.map(length).get(), is(0));
        assertThat(nullstring.map(greaterThan(0), length), is(Singular.<Integer>none()));
    }

}
