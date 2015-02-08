package no.motif.maps;

import static no.motif.Base.containedIn;
import static no.motif.Base.first;
import static no.motif.Base.is;
import static no.motif.Base.where;
import static no.motif.Iterate.on;
import static no.motif.Maps.keyIn;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import no.motif.Maps;
import no.motif.f.Fn;

/**
 * Documentation in {@link no.motif.Maps#combine(Map, Map) Maps.combine(..)}
 *
 * @param <K1> The key type of the first map, and the effective key type of the map.
 * @param <V1> The value type of the first map
 * @param <K2> The key type of the second map.
 * @param <V2> The value type of the second map, and the effective value type of the map.
 *
 * @see no.motif.Maps#combine(Map, Map)
 */
public class CombinedMapsView<K1, V1, K2, V2> implements Map<K1, V2> {

    private final Map<K1, V1> m1;
    private final Map<K2, V2> m2;
    private final Fn<? super V1, ? extends K2> connector;
    private final Fn<Entry<?, V1>, ? extends K2> m1MappedValue;


    public CombinedMapsView(Map<K1, V1> m1, Fn<? super V1, ? extends K2> connector, Map<K2, V2> m2) {
        this.m1 = m1;
        this.m2 = m2;
        this.connector = connector;
        this.m1MappedValue = first(Maps.<V1>value()).then(connector);
    }


    @Override
    public int size() {
        return on(m1.values()).map(connector).filter(containedIn(m2.keySet())).collect().size();
    }


    @Override
    public boolean isEmpty() {
        return m1.isEmpty()
            || m2.isEmpty()
            || on(m1).filter(where(m1MappedValue, keyIn(m2))).isEmpty();
    }


    @Override
    public boolean containsKey(Object key) {
        return m1.containsKey(key) && m2.containsKey(m1.get(key));
    }


    @Override
    public boolean containsValue(Object value) {
        if (m2.containsValue(value)) {
            Entry<K2, V2> entry = on(m2).filter(where(Maps.<V2>value(), is(value))).head().get();
            return m1.containsValue(entry.getKey());
        }
        return false;
    }


    @Override
    public V2 get(Object key) {
        if (!m1.containsKey(key)) return null;
        else return m2.get(m1.get(key));
    }


    @Override
    public V2 put(K1 key, V2 value) {
        throw new UnsupportedOperationException("put is not supported");
    }


    @Override
    public V2 remove(Object key) {
        if (this.containsKey(key)) {
            return m2.remove(m1.remove(key));
        }
        return null;
    }


    @Override
    public void putAll(Map<? extends K1, ? extends V2> m) {
        throw new UnsupportedOperationException("putAll is not supported");
    }


    @Override
    public void clear() {
        for (Entry<K1, V1> entry : on(m1).filter(where(m1MappedValue, keyIn(m2))).eval()) {
            m1.remove(entry.getKey());
            m2.remove(entry.getValue());
        }
    }


    @Override
    public Set<K1> keySet() {
        return on(m1)
                .filter(where(m1MappedValue, keyIn(m2)))
                .map(Maps.<K1>key())
                .collectIn(new LinkedHashSet<K1>());
    }


    @Override
    public Collection<V2> values() {
        return on(m2)
                .filter(where(Maps.<K2>key(), containedIn(on(m1).map(m1MappedValue))))
                .map(Maps.<V2>value())
                .collect();
    }


    @Override
    public Set<Entry<K1, V2>> entrySet() {
        return on(m1)
                .filter(where(m1MappedValue, keyIn(m2)))
                .map(toEntry)
                .collectIn(new LinkedHashSet<Entry<K1, V2>>());
    }

    private final Fn<Entry<K1, V1>, Entry<K1, V2>> toEntry = new Fn<Entry<K1, V1>, Entry<K1, V2>>() {
        @Override
        public Entry<K1, V2> $(Entry<K1, V1> entry) {
            return new MapsViewEntry(entry.getKey(), connector.$(entry.getValue()));
        }};


    private final class MapsViewEntry implements Map.Entry<K1, V2> {

        private final K1 key;
        private final K2 key2;

        public MapsViewEntry(K1 key, K2 key2) {
            this.key = key;
            this.key2 = key2;
        }

        @Override
        public K1 getKey() {
            return key;
        }

        @Override
        public V2 getValue() {
            return m2.get(key2);
        }

        @Override
        public V2 setValue(V2 value) {
            return m2.put(key2, value);
        }

    }

}
