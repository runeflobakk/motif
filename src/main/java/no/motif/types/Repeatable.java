package no.motif.types;

public interface Repeatable<T> {

    /**
     * Repeat the contained elements a given amount of times.
     * <ul>
     * <li>[].repeat(x) == []</li>
     * <li>Xs.repeat(0) == []</li>
     * <li>Xs.repeat(1) == Xs</li>
     * <li>[x, y].repeat(3) == [x, y, x, y, x, y]</li>
     * <li>[x, y].repeat(-1) throws {@link IllegalArgumentException} (eventually)</li>
     * </ul>
     *
     * @param times The amount to repeat.
     *
     * @return The elements, repeated the given amount of times.
     */
    Repeatable<T> repeat(int times);

}
