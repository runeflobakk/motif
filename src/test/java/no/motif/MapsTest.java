package no.motif;

import static no.motif.Base.equalOrGreaterThan;
import static no.motif.Base.where;
import static no.motif.Iterate.on;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class MapsTest {

    @Test
    public void filterAndMapOnMapKeysAndValues() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        assertThat(on(map)
                .filter(where(Maps.<Integer>key(), equalOrGreaterThan(2)))
                .map(Maps.<String>value()),
                contains("b", "c"));
    }
}
