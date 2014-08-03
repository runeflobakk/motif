package no.motif;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static no.motif.Base.always;
import static no.motif.Base.first;
import static no.motif.Base.toString;
import static no.motif.Iterate.empty;
import static no.motif.Iterate.on;
import static no.motif.Singular.optional;
import static no.motif.Singular.the;
import static no.motif.Strings.before;
import static no.motif.Strings.toChars;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyIterable;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import no.motif.f.Fn;
import no.motif.iter.SimpleIterator;
import no.motif.single.Optional;
import no.motif.types.Elements;

import org.junit.Test;

public class IterateTest {

    @Test
    public void iteratingNullIsEmptyIterable() {
        assertThat(on((String) null), sameInstance(Iterate.<Character>none()));
        assertThat(on((Integer[]) null), sameInstance(Iterate.<Integer>none()));
        assertThat(on((Iterable<Integer>) null), sameInstance(Iterate.<Integer>none()));
        assertThat(on((byte[]) null), sameInstance(Iterate.<Byte>none()));
    }


    @Test
    public void emptyPredicate() {
        assertFalse(empty.$(asList(1)));
        assertTrue(empty.$(emptyList()));
    }


    @Test
    public void bridgeArrayYieldingFunctionToIterableYieldingFunction() {
        Fn<Object, Integer[]> oneTwoThree = new Fn<Object, Integer[]>() {
            @Override
            public Integer[] $(Object value) {
                return new Integer[] {1, 2, 3};
            }
        };
        assertThat(optional("").split(Iterate.toIterable(oneTwoThree)), contains(1, 2, 3));
    }

    @Test
    public void iterateAMapAsKeyAndValueEntries() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "c");
        assertThat(on(map), contains(map.entrySet().toArray()));
    }

    @Test
    public void iterateANullMapYieldsEmptyEntries() {
        assertThat(on((Map<?, ?>) null), emptyIterable());
    }

    @Test
    public void functionsAreAppliedOnEvalAndNeverAgain() {
        @SuppressWarnings("unchecked")
        Fn<String, String> register = mock(Fn.class);
        when(register.$(anyString())).then(returnsFirstArg());

        Elements<String> elements = on(100, 200).map(first(toString).then(register));
        verifyZeroInteractions(register);
        elements = elements.eval();
        verify(register).$("100");
        verify(register).$("200");
        verifyNoMoreInteractions(register);
        assertThat(elements.map(before(1)), contains("1", "2"));
        verifyNoMoreInteractions(register);
    }

    @Test
    public void flatMap() {
        assertThat(on("ab", "cd").flatMap(toChars), contains('a', 'b', 'c', 'd'));
        assertThat(on(1, 2).flatMap(always(emptyList())), emptyIterable());
    }

    @Test
    public void consumingStreamLikeIterables() {
        Iterable<Integer> naturals = new Iterable<Integer>() {
            int current = 1;
            @Override
            public Iterator<Integer> iterator() {
                return new SimpleIterator<Integer>() {
                    @Override protected Optional<Integer> nextIfAvailable() throws Exception {
                        return the(current++).asOptional();
                    }}; }
        };

        assertThat(on(naturals).take(4), contains(1, 2, 3, 4));
    }

    @Test
    public void iterateEmptyJavaCollectionYieldsNoneInstance() {
        assertThat(on(new ArrayList<>()), sameInstance(Iterate.none()));
    }

}
