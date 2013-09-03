package no.motif;

import java.io.PrintStream;

import no.motif.f.Do;

/**
 * IO-operations.
 */
public final class IO {

    /**
     * {@link PrintStream#println(Object) Print} to a <code>PrintStream</code>,
     * each call adding a line feed at the end.
     *
     * @param out the {@link PrintStream} to print to.
     * @return The <code>println</code> {@link Do} operation.
     */
    public static final <T> Do<T> println(final PrintStream out) {
        return new Do<T>() { @Override public void $(T value) { out.println(value); }}; }


    /**
     * {@link PrintStream#print(Object) Print} to a <code>PrintStream</code>.
     *
     * @param out the {@link PrintStream} to print to.
     * @return The <code>print</code> {@link Do} operation.
     */
    public static final <T> Do<T> print(final PrintStream out) {
        return new Do<T>() { @Override public void $(T value) { out.print(value); }}; }


    private IO() {}
}
