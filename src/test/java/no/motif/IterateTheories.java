package no.motif;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;

@RunWith(Theories.class)
public class IterateTheories {

    @Theory
    public void sizeOfCollection(@ForAll(sampleSize=50) List<?> anyList) {
        assertThat(anyList, hasSize(Iterate.size.$(anyList)));
    }

}
