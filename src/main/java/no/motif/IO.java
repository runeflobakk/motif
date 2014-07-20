package no.motif;

import static no.motif.Exceptions.asRuntimeException;

import java.io.IOException;
import java.io.OutputStream;
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
        return new Do<T>() { @Override public void with(T value) { out.println(value); }}; }


    /**
     * {@link PrintStream#print(Object) Print} to a <code>PrintStream</code>.
     *
     * @param out the {@link PrintStream} to print to.
     * @return The <code>print</code> {@link Do} operation.
     */
    public static final <T> Do<T> print(final PrintStream out) {
        return new Do<T>() { @Override public void with(T value) { out.print(value); }}; }


    /**
     * {@link OutputStream#write(int) Write} bytes to an <code>OutputStream</code>.
     *
     * @param out the {@link OutputStream} to write to.
     * @return The <code>write</code> {@link Do} operation.
     */
    public static final Do<Byte> writeTo(final OutputStream out) {
        return new Do<Byte>() { @Override public void with(Byte b) {
            try { out.write(b); } catch (IOException e) { throw asRuntimeException(e); }
        }};
    }


    private IO() {}
}
