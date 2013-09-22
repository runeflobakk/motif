package no.motif;

import java.util.Map;
import java.util.Map.Entry;

import no.motif.f.Fn;

/**
 * Operations on {@link Map maps} and {@link Entry map entries}.
 */
public final class Maps {


    /**
     * Gets the key of a {@link Entry map entry}.
     */
    public static <K> Fn<Entry<K, ?>, K> mapkey() { return new Fn<Map.Entry<K, ?>, K>() {
        @Override public K $(Entry<K, ?> entry) { return entry.getKey(); }}; }

    /**
     * Gets the value of a {@link Entry map entry}.
     */
    public static <V> Fn<Entry<?, V>, V> mapvalue() { return new Fn<Map.Entry<?, V>, V>() {
        @Override public V $(Entry<?, V> entry) { return entry.getValue(); }}; }


    private Maps() {}
}
