package no.motif.maps;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import no.motif.f.Fn;


class FnAsMap<K, V> implements Map<K, V> {

    private final Fn<K, V> fn;

    private final Map<Object, V> overrides;
    private final Map<Object, V> removed;

    FnAsMap(Fn<K, V> fn) {
        this.fn = fn;
        this.overrides = new HashMap<>();
        this.removed = new HashMap<>();
    }


    @Override
    public boolean containsKey(Object key) {
        @SuppressWarnings("unchecked")
        K castedKey = (K) key;
        return !removed.containsKey(key) && (overrides.containsKey(key) || fn.$(castedKey) != null);
    }


    @Override
    public V get(Object key) {
        if (overrides.containsKey(key)) return overrides.get(key);
        if (removed.containsKey(key)) return null;
        @SuppressWarnings("unchecked")
        K castedKey = (K) key;
        return fn.$(castedKey);
    }

    @Override
    public V put(K key, V value) {
        V previous = get(key);
        removed.remove(key);
        overrides.put(key, value);
        return previous;
    }

    @Override
    public V remove(Object key) {
        V previous = get(key);
        removed.put(key, previous);
        overrides.remove(key);
        return previous;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        overrides.clear();
        removed.clear();
    }

    @Override
    public int size() {
        throw new NotPossibleOnMapViewOfFn(fn, "the size cannot be determined"); }

    @Override
    public boolean isEmpty() {
        throw new NotPossibleOnMapViewOfFn(fn, "it cannot be determined if it is empty"); }

    @Override
    public boolean containsValue(Object v) {
        throw new NotPossibleOnMapViewOfFn(fn, "it cannot be determined if it contains the value '" + v + "'"); }

    @Override
    public Set<K> keySet() {
        throw new NotPossibleOnMapViewOfFn(fn, "it is not possible derive a set of keys."); }

    @Override
    public Collection<V> values() {
        throw new NotPossibleOnMapViewOfFn(fn, "it is not possible resolve the values."); }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        throw new NotPossibleOnMapViewOfFn(fn, "it is not possible derive the entries."); }

}
