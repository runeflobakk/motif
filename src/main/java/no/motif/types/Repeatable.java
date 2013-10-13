package no.motif.types;

public interface Repeatable<T> {

    /**
     * Repeat the sequence of contained elements a given amount of times.
     * <ul>
     * <li>[x, y].repeat(3) == [x, y, x, y, x, y]</li>
     * <li>[x, y].repeat(1) == [x, y]</li>
     * <li>[x, y].repeat(0) == []</li>
     * </ul>
     * @param times The amount to repeat.
     *
     * @return The elements, repeated the given amount of times.
     */
    Repeatable<T> repeat(int times);

}
