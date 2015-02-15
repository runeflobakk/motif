package no.motif;

import static java.util.Collections.emptyMap;
import static no.motif.Base.always;
import static no.motif.Base.equalOrGreaterThan;
import static no.motif.Base.where;
import static no.motif.Ints.increment;
import static no.motif.Iterate.on;
import static no.motif.Maps.asFn;
import static no.motif.Maps.asMap;
import static no.motif.Maps.combine;
import static no.motif.Maps.valueIn;
import static no.motif.Strings.upperCased;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import no.motif.f.Do;
import no.motif.f.Fn;
import no.motif.maps.NotPossibleOnMapViewOfFn;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;

@RunWith(Theories.class)
public class MapsTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();


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


    @Theory
    public void keyMappingToValueWhichIsKeyInSecondMap(
            @ForAll(sampleSize=10) Object key,
            @ForAll(sampleSize=10) Object valueKey,
            @ForAll(sampleSize=10) Object value) {

        Map<Object, Object> map = combinedMap(key, valueKey, valueKey, value);
        assertThat(map.get(key), is(value));
        assertThat(map.size(), is(1));
        assertThat(map.keySet(), hasSize(1));
        assertThat(map.values(), hasSize(1));
        assertFalse(map.isEmpty());
        assertTrue(map.containsKey(key));
        assertTrue(map.containsValue(value));

        assertThat(map.remove(key), is(value));
        assertThat(map.size(), is(0));
        assertTrue(map.isEmpty());
        assertFalse(map.containsKey(key));
        assertFalse(map.containsValue(value));
    }

    @Theory
    public void keyMappingToValueWhichIsNotKeyInSecondMap(
            @ForAll(sampleSize=1) Object key1,
            @ForAll(sampleSize=10) Object value1,
            @ForAll(sampleSize=10) Object key2,
            @ForAll(sampleSize=1) Object value2) {
        assumeThat(value1, not(key2));

        Map<Object, Object> map = combinedMap(key1, value1, key2, value2);
        assertThat(map.get(key1), nullValue());
        assertThat(map.size(), is(0));
        assertThat(map.keySet(), hasSize(0));
        assertThat(map.values(), hasSize(0));
        assertTrue(map.isEmpty());
        assertFalse(map.containsKey(key1));
        assertFalse(map.containsValue(value2));

        Object otherKey = "otherKey";
        Object otherValue = "otherValue";
        assumeThat(otherKey, not(key1));
        assumeThat(otherValue, not(value2));
        assertFalse(map.containsKey(otherKey));
        assertFalse(map.containsValue(otherValue));
    }

    @Theory
    public void neverContainsValueWhichIsNotInSecondMap(
            @ForAll(sampleSize=10) LinkedHashMap<String, Integer> first,
            @ForAll(sampleSize=10) List<Character> valuesInSecond,
            @ForAll(sampleSize=10) Character valueNotInSecond) {

        assumeThat(valuesInSecond, not(hasItem(valueNotInSecond)));

        Queue<Integer> valuesInFirst = new LinkedList<>(first.values());
        Map<Integer, Character> second = new LinkedHashMap<>();
        for (Character value : valuesInSecond) {
            if (valuesInFirst.isEmpty()) break;
            second.put(valuesInFirst.poll(), value);
        }
        Map<String, Character> combined = combine(first, second);
        assertThat(combined.values(), containsInAnyOrder(on(valuesInSecond).take(first.size()).collect().toArray()));
        assertThat(combined, not(hasValue(valueNotInSecond)));
    }

    @Theory
    public void getReturnsNullForKeysNotInFirstMap(
            @ForAll(sampleSize=10) String key,
            @ForAll(sampleSize=10) HashMap<String, ?> map) {

        assumeThat(map, not(hasKey(key)));
        assertThat(combine(map, null).get(key), nullValue());


        Map<Integer, String> first = new HashMap<>();
        first.put(1, key);
        assertThat(combine(first, map).get(1), nullValue());

    }



    @Test
    public void putIsNotSupported() {
        Map<Integer, String> map = combinedMap(1, 2, 2, "value");
        expectedException.expect(UnsupportedOperationException.class);
        map.put(2, "new");
    }

    @Test
    public void putAllIsNotSupported() {
        Map<Integer, String> map = combinedMap(1, 2, 2, "value");
        expectedException.expect(UnsupportedOperationException.class);
        map.putAll(new HashMap<Integer, String>());
    }

    @Theory
    public void canSetNewValuesWithEntrySet(
            @ForAll(sampleSize=7) Object key,
            @ForAll(sampleSize=7) String value,
            @ForAll(sampleSize=10) String newValue) {

        Map<Object, String> map = combinedMap(key, "x", "x", value);
        assertThat(map.get(key), is(value));
        for (Entry<?, String> entry : map.entrySet()) entry.setValue(newValue);
        assertThat(map.get(key), is(newValue));
    }

    @Theory
    public void emptyIfOneOfTheMapsAreEmpty(@ForAll(sampleSize=5) Map<Object, Object> map) {
        assumeFalse(map.isEmpty());
        assertTrue(combine(emptyMap(), map).isEmpty());
        assertTrue(combine(map, emptyMap()).isEmpty());
    }

    @Theory
    public void removingFromEmptyCombinedMapDoesNotRemoveFromUnderlyingMaps(
            @ForAll(sampleSize=2) Object nonExistingKey,
            @ForAll(sampleSize=2) Object key1,
            @ForAll(sampleSize=4) Object value1,
            @ForAll(sampleSize=4) Object key2,
            @ForAll(sampleSize=2) Object value2) {
        assumeThat(value1, not(key2));

        Map<Object, Object> first = new HashMap<>();
        first.put(key1, value1);
        Map<Object, Object> second = new HashMap<>();
        second.put(key2, value2);
        Map<?, ?> combined = combine(first, second);

        assertNull(combined.remove(key1));
        assertNull(combined.remove(nonExistingKey));
        combined.clear();
        assertThat(combined.size(), is(0));

        assertThat(first.get(key1), is(value1));
        assertThat(first.size(), is(1));
        assertThat(second.get(key2), is(value2));
        assertThat(second.size(), is(1));


        Object x = "x";
        Object y = "y";
        Object z = "z";
        assumeThat(first, not(hasKey(x)));
        assumeThat(second, not(hasKey(y)));
        first.put(x, y);
        second.put(y, z);
        assertThat(combined.size(), is(1));
        assertThat(first.size(), is(2));
        assertThat(second.size(), is(2));

        combined.clear();
        assertThat(combined.size(), is(0));
        assertThat(first.size(), is(1));
        assertThat(second.size(), is(1));
    }

    @Theory
    public void valueInPredicate(@ForAll Object notInMap) {
        Object inMap = "value";
        assumeThat(notInMap, not(inMap));
        Map<?, Object> mapWithObject = combinedMap("x", "y", "y", inMap);
        assertTrue(valueIn(mapWithObject).$(inMap));
        assertFalse(valueIn(mapWithObject).$(notInMap));
    }


    private <K, I, V> Map<K, V> combinedMap(K key, I internalValue, I internalKey, V value) {
        Map<K, I> map1 = new HashMap<>();
        Map<I, V> map2 = new HashMap<>();
        map1.put(key, internalValue);
        map2.put(internalKey, value);
        return Maps.combine(map1, map2);
    }


    @Theory
    public void mapAsFnYieldsValuesOf(@ForAll Map<Object, Object> anyMap) {
        Fn<Object, Object> fn = asFn(anyMap);
        for (Entry<?, Object> entry : anyMap.entrySet()) {
            assertThat(fn.$(entry.getKey()), is(entry.getValue()));
        }
    }


    @Theory
    public void fnAsMapKeyQuery(@ForAll String key) {
        assertTrue(asMap(upperCased).containsKey(key));
        assertThat(asMap(upperCased).get(key), is(upperCased.$(key)));
        assertFalse(asMap(always(null)).containsKey(key));
    }


    @Theory
    public void aPutValueCanBeRetrievedByItsKey(@ForAll Integer value) {
        Map<Number, Integer> increments = asMap(increment);
        Integer previous = increments.get(42);
        Integer replaced = increments.put(42, value);
        assertThat(increments.get(42), is(value));
        assertThat(replaced, is(previous));
    }

    @Theory
    public void afterAnEntryHasBeenPutTheMapContainsThatKey(@ForAll Integer key) {
        Map<Object, Object> map = asMap(always(null));
        assertFalse(map.containsKey(key));
        map.put(key, 42);
        assertTrue(map.containsKey(key));
    }

    @Theory
    public void afterAMapHasBeenPutTheMapContainsTheEntriesOfThatMap(@ForAll Map<Integer, Integer> putMap) {
        Map<Object, Object> map = asMap(always(null));
        map.putAll(putMap);
        for (int key : putMap.keySet()) {
            assertTrue(map.containsKey(key));
        }
    }

    @Theory
    public void mapDoesNotContainKeyAfterItHasBeenRemoved(@ForAll String key) {
        Map<String, String> map = asMap(upperCased);
        assertTrue(map.containsKey(key));
        String removed = map.remove(key);
        assertThat(removed, is(upperCased.$(key)));
        assertFalse(map.containsKey(key));

        map.put(key, "value");
        assertTrue(map.containsKey(key));
        removed = map.remove(key);

        assertThat(removed, is("value"));
        assertFalse(map.containsKey(key));
        assertNull(map.get(key));
    }

    @Theory
    public void clearedFnAsMapIsEmpty(@ForAll String key) {
        Map<String, String> map = asMap(upperCased);
        map.clear();
        assertNull(map.get(key));
        assertTrue("empty map", map.isEmpty());
    }

    @Test
    public void unsupportedOperationsBecomeSupportedAfterAMapViewOfFnIsCleared() {
        Map<String, Do<Map<String, String>>> unsupported = new HashMap<>();
        unsupported.put("containsValue",
                new Do<Map<String, String>>() { @Override public void with(Map<String, String> m) {m.containsValue("a"); }});
        unsupported.put("entrySet",
                new Do<Map<String, String>>() { @Override public void with(Map<String, String> m) {m.entrySet(); }});
        unsupported.put("keySet",
                new Do<Map<String, String>>() { @Override public void with(Map<String, String> m) {m.keySet(); }});
        unsupported.put("values",
                new Do<Map<String, String>>() { @Override public void with(Map<String, String> m) {m.values(); }});
        unsupported.put("size",
                new Do<Map<String, String>>() { @Override public void with(Map<String, String> m) {m.size(); }});
        unsupported.put("isEmpty",
                new Do<Map<String, String>>() { @Override public void with(Map<String, String> m) {m.isEmpty(); }});

        for (Entry<String, Do<Map<String, String>>> operation : unsupported.entrySet()) {
            assertUnsupportedUntilCleared(asMap(upperCased), operation);
        }
    }

    private <K, V> void assertUnsupportedUntilCleared(Map<K, V> map, Entry<String, Do<Map<K, V>>> operation) {
        try {
            operation.getValue().with(map);
        } catch (NotPossibleOnMapViewOfFn e) {
            map.clear();
            operation.getValue().with(map);
            return;
        }
        fail(operation.getKey() + " should throw exception");
    }

}
