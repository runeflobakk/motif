package no.motif.maps;

import static no.motif.Base.containedIn;
import static no.motif.Base.is;
import static no.motif.Base.where;
import static no.motif.Iterate.on;
import static no.motif.Maps.keyIn;
import static no.motif.Maps.valueIn;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import no.motif.Maps;
import no.motif.f.Fn;

/**
 * Documentation in {@link no.motif.Maps#combine(Map, Map) Maps.combine(..)}
 *
 * @param <K> The key type.
 * @param <I> The "connecting" type of the two maps.
 * @param <V> The value type.
 *
 * @see no.motif.Maps#combine(Map, Map)
 */
public class CombinedMapsView<K, I, V> implements Map<K, V> {

    private final Map<K, I> m1;
    private final Map<I, V> m2;


    public CombinedMapsView(Map<K, I> m1, Map<I, V> m2) {
        this.m1 = m1;
        this.m2 = m2;
    }


    @Override
    public int size() {
        return on(m1.values()).filter(containedIn(m2.keySet())).collect().size();
    }


    @Override
    public boolean isEmpty() {
        return m1.isEmpty()
            || m2.isEmpty()
            || on(m1).filter(where(Maps.<I>value(), keyIn(m2))).isEmpty();
    }


    @Override
    public boolean containsKey(Object key) {
        return m1.containsKey(key) && m2.containsKey(m1.get(key));
    }


    @Override
    public boolean containsValue(Object value) {
        if (m2.containsValue(value)) {
            Entry<? super I, V> entry = on(m2).filter(where(Maps.<V>value(), is(value))).head().get();
            return m1.containsValue(entry.getKey());
        }
        return false;
    }


    @Override
    public V get(Object key) {
        if (!m1.containsKey(key)) return null;
        else return m2.get(m1.get(key));
    }


    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException("put is not supported");
    }


    @Override
    public V remove(Object key) {
        if (this.containsKey(key)) {
            return m2.remove(m1.remove(key));
        }
        return null;
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("putAll is not supported");
    }


    @Override
    public void clear() {
        for (Entry<K, I> entry : on(m1).filter(where(Maps.<I>value(), keyIn(m2))).eval()) {
            m1.remove(entry.getKey());
            m2.remove(entry.getValue());
        }
    }


    @Override
    public Set<K> keySet() {
        return on(m1)
                .filter(where(Maps.<I>value(), keyIn(m2)))
                .map(Maps.<K>key())
                .collectIn(new LinkedHashSet<K>());
    }


    @Override
    public Collection<V> values() {
        return on(m2)
                .filter(where(Maps.<I>key(), valueIn(m1)))
                .map(Maps.<V>value())
                .collect();
    }


    @Override
    public Set<Entry<K, V>> entrySet() {
        return on(m1)
                .filter(where(Maps.<I>value(), keyIn(m2)))
                .map(toEntry)
                .collectIn(new LinkedHashSet<Entry<K, V>>());
    }

    private final Fn<Entry<K, I>, Entry<K, V>> toEntry = new Fn<Map.Entry<K,I>, Entry<K, V>>() {
        @Override
        public Entry<K, V> $(Entry<K, I> entry) {
            return new MapsViewEntry(entry.getKey(), entry.getValue());
        }};


    private final class MapsViewEntry implements Map.Entry<K, V> {

        private final K key;
        private final I key2;

        public MapsViewEntry(K key, I key2) {
            this.key = key;
            this.key2 = key2;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return m2.get(key2);
        }

        @Override
        public V setValue(V value) {
            return m2.put(key2, value);
        }

    }

}
