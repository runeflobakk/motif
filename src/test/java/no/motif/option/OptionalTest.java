package no.motif.option;

import static no.motif.Base.not;
import static no.motif.Iterate.on;
import static no.motif.Strings.blank;
import static no.motif.Strings.lowerCased;
import static no.motif.Strings.toChars;
import static no.motif.Strings.upperCased;
import static no.motif.Strings.trimmed;
import static no.motif.Singular.none;
import static no.motif.Singular.optional;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import no.motif.Singular;
import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.option.Optional.None;
import no.motif.option.Optional.Some;

import org.junit.Test;


public class OptionalTest {

    @Test
    public void optionalNullIsNone() {
        assertTrue(optional(null) instanceof None);
    }

    @Test
    public void optionalValueIsSome() {
        assertTrue(optional("value") instanceof Some);
    }

    @Test
    public void iteratesOneTimeOverSome() {
        int iterations = 0;
        for (String value : optional("value")) {
            assertThat(value, is("value"));
            iterations++;
        }
        assertThat(iterations, is(1));
    }

    @Test
    @SuppressWarnings("unused")
    public void doesNotIterateOverNone() {
        for (Object value : none()) fail("should not iterate over None");
    }

    @Test(expected = NoSuchElementException.class)
    public void throwsExceptionIfGettingAValueFromNone() {
        none().get();
    }

    @Test
    public void getTheValue() {
        assertThat(optional("value").get(), is("value"));
    }

    @Test
    public void mapTheValue() {
        assertThat(
                optional("    VALUE    ")
                .map(lowerCased).map(trimmed).get(), is("value"));
    }

    @Test
    public void severalMappingsOfSomeValue() {
        assertThat(optional(" X ").map(lowerCased).map(trimmed).get(), is("x"));
    }

    @Test
    public void severalMappingOfNone() {
        assertThat(optional((String) null).map(lowerCased).map(trimmed).getOrElse("y"), is("y"));
    }

    @Test
    public void itIsPossibleToMapANone() {
        String nonExistingString = null;
        for (String none : optional(nonExistingString).map(lowerCased)) {
            fail("it is possible to map a None, but it should not iterate. Got value " + none);
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void throwsExceptionWhenGettingAMappedNone() {
        String nonExistingString = null;
        optional(nonExistingString).map(upperCased).get();
    }

    @Test
    public void canAskIfValueIsPresent() {
        assertTrue(optional("value").isSome());
        assertFalse(optional(null).isSome());
    }

    @Test
    public void canDecideSomeOrNoneBasedOnPredicate() {
        assertThat(optional(not(blank), "").isSome(), is(false));
        assertThat(optional(not(blank), null).isSome(), is(false));
        assertTrue(optional(not(blank), "abc").isSome());
    }

    @Test
    public void getOrElseOnNoneReturnsElse() {
        assertThat(optional((String) null).getOrElse("else"), is("else"));
    }

    @Test
    public void getOrElseOnSomeReturnsTheOptionalValue() {
        assertThat(optional("some").getOrElse("else"), is("some"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removingFromAnOptionalIteratorIsAbsurd() {
        optional("x").iterator().remove();
    }

    @Test
    public void mapperFunctionReturningNullCreatesNone() {
        Optional<?> none = optional("whatever").map(new Fn<Object, Object>() { @Override public Object $(Object value) { return null; }});
        assertFalse(none.isSome());
    }

    @Test
    public void mappingToNoneWithFailingPredicate() {
        Optional<?> none = optional("   ").map(not(blank), trimmed);
        assertFalse(none.isSome());
    }

    @Test
    public void mappingNoneAlwaysGiveSameNone() {
        Optional<String> none = optional((String) null);
        Optional<String> mappedNone1 = none.map(lowerCased);
        Optional<String> mappedNone2 = none.map(new Predicate<String>() {
            @Override public boolean $(String value) { return true; }
        }, upperCased);

        assertThat(none, sameInstance(mappedNone1));
        assertThat(none, sameInstance(mappedNone2));
    }

    @Test
    public void splitToIterable() {
        assertThat(optional("123").split(toChars), contains('1', '2', '3'));
        assertThat(Singular.<String>none().split(toChars), emptyIterable());
    }

    @Test
    public void splittingUsingFunctionYieldingPreparedIterableReturnsThatSameInstance() {
        final Iterable<Character> chars = on("xyz");
        Iterable<Character> splittedString = optional("").split(new Fn<Object, Iterable<Character>>() {
            @Override public Iterable<Character> $(Object o) { return chars; }});
        assertThat(splittedString, sameInstance(chars));
    }


}
