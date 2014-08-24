package no.motif.f;

import static no.motif.Base.first;
import static no.motif.Base.toString;
import static no.motif.Ints.increment;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FnChainTest {

    @Test
    public void fnChainWithEndingSideeffect() {
        final List<String> result = new ArrayList<>();
        on(1, 2, 3).each(first(increment).then(toString).then(new Do<String>() {
            @Override public void with(String value) { result.add(value); }}).asDo);

        assertThat(result, contains("2", "3", "4"));
    }
}
