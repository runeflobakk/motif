package no.motif;

import java.util.Map;
import java.util.Map.Entry;

import no.motif.f.Fn;
import no.motif.f.Predicate;
import no.motif.maps.CombinedMapsView;
import no.motif.maps.MapViewOfFn;
import no.motif.maps.NotPossibleOnMapViewOfFn;

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
     * Use a given {@link Fn} as a {@link java.util.Map}.
     * Any key which resolves to <code>null</code> using the given </code>Fn</code> are not
     * considered to be {@link Map#containsKey(Object) contained} in the map, all other keys
     * are contained in the map.
     *
     * <p>
     * This imposes some very limiting
     * constraints on the returned <code>Map</code>. Especially, any method which
     * queries {@link Map#containsValue(Object) values without a key}, its {@link Map#size() size},
     * whether it {@link Map#isEmpty() is empty}, and retrieving all
     * {@link Map#keySet() keys}, {@link Map#values() values} and {@link Map#entrySet() entries}
     * is not possible, and will throw {@link NotPossibleOnMapViewOfFn}.
     *
     * <p>
     * The returned <code>Map</code> is mostly useable for retrieval of values based on
     * keys which must be known beforehand. If the map is {@link Map#clear() cleared}, it
     * "becomes" a {@link java.util.HashMap}, and thus will fully adhere to the <code>Map</code>
     * contract as its entries are no longer being dependent of the <code>Fn</code>.
     *
     * <p>
     * Fully supported <code>Map</code>-operations:
     * <ul>
     *   <li> {@link Map#containsKey(Object)}</li>
     *   <li> {@link Map#get(Object)}</li>
     *   <li> {@link Map#put(Object, Object)}</li>
     *   <li> {@link Map#putAll(Map)}</li>
     *   <li> {@link Map#remove(Object)}</li>
     *   <li> {@link Map#clear()}</li>
     * </ul>
     *
     * In conclusion, the behavior of the returned map seriously violates the contract of
     * the <code>Map</code> interface, but it may still be appropriate for certain cases.
     * Careful considerations should be taken before using a <code>Map</code> returned by this method.
     */
    public static <K, V> Map<K, V> asMap(Fn<K, V> fn) {
        return new MapViewOfFn<>(fn);
    }


    /**
     * Uses a {@link java.util.Map} as the basis for a {@link Fn}.
     * The value of the <code>Fn</code> is resolved simply my calling
     * {@link Map#get(Object) .get(I)} on the map with the given argument for
     * {@link Fn#$(Object) Fn.$(I)}. The semantics for <code>null</code> are the
     * same as for {@link Map#get(Object)}; the <code>Fn</code> yields <code>null</code>
     * both for keys not present in the map, and for mapped <code>null</code> values if
     * the map permits it.
     */
    public static <I, O> Fn<I, O> asFn(final Map<I, O> map) { return new Fn<I, O>() { @Override public O $(I key) {
        return map.get(key);
    }}; }


    /**
     * Creates a combined view of two maps, where the values of the first map corresponds
     * to keys of the second map.
     *
     * @see #combine(Map, Fn, Map)
     */
    public static <K, I, V> Map<K, V> combine(Map<K, I> first, Map<I, V> second) {
        return combine(first, NOP.<I>fn(), second);
    }


    /**
     * Creates a combined view of two maps, where the values of the first map will be mapped (no pun
     * intended) to keys of the second map. This has the implication that any value in the first map not
     * being mapped to a key in the second map will appear as not present in this view. The same applies
     * to keys in the second map not being a value (when mapped) in the first map, they cannot be retrieved with
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
     * @param connector A connector {@link Fn} to map values of the first map to keys of the second map.
     * @param second The second map. All or a subset of the values of this map will be values in the returned view.
     * @return the {@link CombinedMapsView}.
     */
    public static <K1, V1, K2, V2> Map<K1, V2> combine(
            Map<K1, V1> first,
            Fn<? super V1, ? extends K2> connector,
            Map<K2, V2> second) {

        return new CombinedMapsView<K1, V1, K2, V2>(first, connector, second);
    }


    private Maps() {}
}
