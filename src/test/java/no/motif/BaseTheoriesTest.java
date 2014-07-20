package no.motif;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Objects;

import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;

@RunWith(Theories.class)
public class BaseTheoriesTest {

    @Theory
    public void hashCode(@ForAll Object anyObject) {
        assertThat(Base.hashCode.$(anyObject), is(Objects.hashCode(anyObject)));
    }
}
