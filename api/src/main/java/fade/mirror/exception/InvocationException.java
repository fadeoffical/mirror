package fade.mirror.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Exception thrown when an error occurs during the invocation of a {@link fade.mirror.Invokable} mirror object.
 *
 * @author fade
 */
public final class InvocationException
        extends MirrorException {

    private InvocationException(String message) {
        super(message);
    }

    private InvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InvocationException from(@NotNull String message, @NotNull Object... format) {
        return new InvocationException(message.formatted(format));
    }

    /**
     * Constructs a new {@link InvocationException} with the specified detail message.
     *
     * @param cause   the cause
     * @param message the detail message
     * @param format  the format arguments
     * @return a new {@link InvocationException} with the specified detail message
     */
    public static InvocationException from(@NotNull Throwable cause, @NotNull String message, @NotNull Object... format) {
        return new InvocationException(message.formatted(format), cause);
    }
}
