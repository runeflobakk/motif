package no.motif;



/**
 * Utilities for dealing with exceptions.
 */
public final class Exceptions {

    /**
     * If you need to rethrow checked exceptions as RuntimeException, this method will
     * ensure a sensible RuntimeException being acquired. Typical use of this method is
     * like this:
     *
     * <pre> try {
     *    someMethod(); // may throw checked Exception
     * } catch (Exception e) {
     *    throw asRuntimeException(e);
     * }</pre>
     *
     *
     * @param e The exception.
     * @return If the given exception is a RuntimeException, it will be returned as-is
     *         casted to RuntimeException, or else the exception will be wrapped in a
     *         new RuntimeException.
     */
    public static RuntimeException asRuntimeException(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            RuntimeException rtEx = new RuntimeException(e.getClass().getName() + ": " + e.getMessage(), e);
            rtEx.setStackTrace(e.getStackTrace());
            return rtEx;
        }
    }

    private Exceptions() {}
}
