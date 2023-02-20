package fade.mirror.internal.exception;

/**
 * Represents a generic exception that can be thrown by the mirror API.
 *
 * @author fade
 */
public class MirrorException
        extends RuntimeException {

    /**
     * Constructs a new exception with null as its detail message.
     */
    MirrorException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message
     */
    MirrorException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    MirrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause.
     *
     * @param cause the cause
     */
    MirrorException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message, cause, suppression enabled or disabled, and
     * writable stack trace enabled or disabled.
     *
     * @param message            the detail message
     * @param cause              the cause
     * @param enableSuppression  whether suppression is enabled or disabled
     * @param writableStackTrace whether the stack trace should be writable
     */
    MirrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
