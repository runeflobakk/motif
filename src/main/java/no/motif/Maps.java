package no.motif;

import java.util.Map;
import java.util.Map.Entry;

import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.maps.CombinedMapsView;

/**
 * Operations on {@link Map maps} and {@link Entry map entries}.
 */
public final class Maps {


    /**
     * Gets the key of a {@link Entry map entry}.
     */
    public static <K> Fn<Entry<K, ?>, K> key() { return new Fn<Map.Entry<K, ?>, K>() {
        @Override public K $(Entry<K, ?> entry) { return entry.getKey(); }}; }


    /**
     * Gets the value of a {@link Entry map entry}.
     */
    public static <V> Fn<Entry<?, V>, V> value() { return new Fn<Map.Entry<?, V>, V>() {
        @Override public V $(Entry<?, V> entry) { return entry.getValue(); }}; }



    /**
     * Evaluate if objects are a key {@link Map#containsKey(Object) contained in}
     * the given <code>Map</code>.
     */
    public static <K> Predicate<K> keyIn(final Map<K, ?> map) { return new Predicate<K>() {
        @Override public boolean $(K key) { return map.containsKey(key); }}; }


    /**
     * Evaluate if objects are a value {@link Map#containsValue(Object) contained in}
     * the given <code>Map</code>.
     */
    public static <V> Predicate<V> valueIn(final Map<?, ? extends V> map) { return new Predicate<V>() {
        @Override public boolean $(V value) { return map.containsValue(value); }}; }


    /**
     * Creates a combined view of two maps, where the values of the first map corresponds
     * to keys of the second map. This has the implication that any value in the first map not
     * being a key in the second map will appear as not present in this view. The same applies
     * to keys in the second map not being a value in the first map, they cannot be retrieved with
     * this view.
     * <p>
     * Similarily, the semantics of the {@link Map#containsKey(Object) containsKey(key)} works transitively.
     * It returns true <em>only</em> if the given key is contained in the first map, and yields a value
     * which is contained as a key in the second map. The usual behavior considering possible
     * <code>null</code>s as keys and values applies as specified in the {@link Map} interface, and is
     * dependent of the two maps the view is based on.
     * </p>
     *
     * @param first The first map. All or a subset of the keys of this map will be keys in the returned view.
     * @param second The second map. All or a subset of the values of this map will be values in the returned view.
     * @return the {@link CombinedMapsView}.
     */
    public static <K, I, V> Map<K, V> combine(Map<K, I> first, Map<I, V> second) {
        return new CombinedMapsView<K, I, V>(first, second);
    }


    private Maps() {}
}
