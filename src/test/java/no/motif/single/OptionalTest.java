package no.motif.single;

import static no.motif.Base.always;
import static no.motif.Base.not;
import static no.motif.Base.notNull;
import static no.motif.Iterate.on;
import static no.motif.Singular.none;
import static no.motif.Singular.optional;
import static no.motif.Strings.blank;
import static no.motif.Strings.lowerCased;
import static no.motif.Strings.nonblank;
import static no.motif.Strings.toChars;
import static no.motif.Strings.trimmed;
import static no.motif.Strings.upperCased;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;

import nl.jqno.equalsverifier.EqualsVerifier;
import no.motif.Singular;
import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.single.Optional.None;
import no.motif.single.Optional.Some;

import org.junit.Test;
import org.objenesis.ObjenesisStd;


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
        try {
            none().iterator().next();
        } catch (NoSuchElementException e) {
            return;
        }
        fail("Should throw " + NoSuchElementException.class.getSimpleName());
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
        assertThat(optional((String) null).map(lowerCased).map(trimmed).orElse("y"), is("y"));
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
    public void orElseOnNoneReturnsElse() {
        assertThat(optional((String) null).orElse("else"), is("else"));
    }

    @Test
    public void orElseOnSomeReturnsTheOptionalValue() {
        assertThat(optional("some").orElse("else"), is("some"));
    }

    @Test
    public void orNullOnNoneIsNull() {
        assertThat(optional(null).orNull(), nullValue());
    }

    @Test
    public void orNullOnSomeIsTheWrappedValue() {
        assertThat(optional("x").orNull(), is("x"));
    }

    @Test
    public void noneOrOtherOptional() {
        assertThat(optional(nonblank, "").or(optional("fallback")), contains("fallback"));
    }

    @Test
    public void someOrOtherOptional() {
        assertThat(optional("a").or(optional("anything")), contains("a"));
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
    public void splittingUsingFunctionYieldingEnhancedIterableReturnsThatSameInstance() {
        final Iterable<Character> chars = on("xyz");
        Iterable<Character> splittedString = optional("").split(new Fn<Object, Iterable<Character>>() {
            @Override public Iterable<Character> $(Object o) { return chars; }});
        assertThat(splittedString, sameInstance(chars));
    }

    @Test
    public void filteringOptional() {
        assertThat(optional("").filter(not(blank)), is(Singular.<String>none()));
        assertThat(optional("x").filter(not(blank)), contains("x"));
        assertThat(none().filter(notNull), is(none()));
    }

    @Test
    public void appending() {
        assertThat(optional("a").append("b"), contains("a", "b"));
        assertThat(optional("a").append(on("b", "c")), contains("a", "b", "c"));
    }

    @Test
    public void prepending() {
        assertThat(optional("World").prepend("Hello"), contains("Hello", "World"));
        assertThat(optional("World").prepend(on("Hello", ",")), contains("Hello", ",", "World"));
    }

    @Test
    public void adheresToEqualsHashcodeContract() {
        EqualsVerifier.forClass(Some.class).verify();
    }

    @Test
    public void deserializedNoneIsRestoredAsTheSingletonInstance() throws Exception {
        ByteArrayOutputStream serialized = new ByteArrayOutputStream();
        try (ObjectOutputStream serializer = new ObjectOutputStream(serialized)) {
            serializer.writeObject(none());
        };
        ByteArrayInputStream bytes = new ByteArrayInputStream(serialized.toByteArray());
        None<?> deserialized;
        try (ObjectInputStream deserializer = new ObjectInputStream(bytes)) {
            deserialized = (None<?>) deserializer.readObject();
        }
        assertSame(deserialized, Singular.none());
        assertTrue(deserialized.equals(Singular.none()));
        assertThat(deserialized.hashCode(), is(Singular.none().hashCode()));
    }

    @Test
    public void sneakilyInstantiatedNewNoneInstanceShouldBeEqualToSingleton() {
        None<?> sneakyNone = new ObjenesisStd().getInstantiatorOf(None.class).newInstance();
        assertFalse(sneakyNone == Singular.none());
        assertTrue(sneakyNone.equals(Singular.none()));
    }

    @Test
    public void noneIsNotEqualToSomeValue() {
        assertThat(Singular.<String>none(), not(equalTo(optional("x"))));
    }

    @Test
    public void hasNiceToString() {
        assertThat(optional(null).toString(), is("None"));
        assertThat(optional("value").toString(), is("Some(value)"));
    }

    @Test
    public void flatMapOnNoneAlwaysYieldsNone() {
        Fn<Object, Optional<String>> toX = always(optional("x"));
        assertThat(none().flatMap(toX), sameInstance(Singular.<String>none()));
    }

    @Test
    public void flatMapToAnotherOptional() {
        Fn<Object, Optional<String>> toY = always(optional("y"));
        assertThat(optional("x").flatMap(toY), contains("y"));
        assertThat(optional("x").flatMap(always(none())), is(none()));
    }

}
