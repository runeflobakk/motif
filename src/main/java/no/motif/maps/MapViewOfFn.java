package no.motif.maps;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import no.motif.Maps;
import no.motif.f.Fn;


/**
 * A {@link java.util.Map} view of an {@link Fn}.
 *
 * @see Maps#asMap(Fn)
 */
public class MapViewOfFn<K, V> implements Map<K, V> {

    private Map<K, V> delegate;

    public MapViewOfFn(Fn<K, V> fn) {
        delegate = new FnAsMap<>(fn);
    }

    @Override
    public int size() {
        return delegate.size();
    }


    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }


    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }


    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }


    @Override
    public V get(Object key) {
        return delegate.get(key);
    }


    @Override
    public V put(K key, V value) {
        return delegate.put(key, value);
    }


    @Override
    public V remove(Object key) {
        return delegate.remove(key);
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        delegate.putAll(m);
    }


    @Override
    public void clear() {
        delegate.clear();
        delegate = new HashMap<>();
    }


    @Override
    public Set<K> keySet() {
        return delegate.keySet();
    }


    @Override
    public Collection<V> values() {
        return delegate.values();
    }


    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return delegate.entrySet();
    }

}
