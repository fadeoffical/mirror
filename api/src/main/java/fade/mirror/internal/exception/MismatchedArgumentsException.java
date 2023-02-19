package fade.mirror.internal.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when the number of arguments passed to an {@link fade.mirror.Invokable} does not match the number of arguments
 * expected by the method or when the type of the arguments passed are not compatible with the types of the arguments.
 *
 * @author fade
 */
public final class MismatchedArgumentsException
        extends MirrorException {

    /**
     * Constructs a new {@link MismatchedArgumentsException} with the specified detail message.
     *
     * @param message the detail message
     */
    private MismatchedArgumentsException(@NotNull String message) {
        super(message);
    }

    /**
     * Constructs a new {@link MismatchedArgumentsException} with the specified detail message.
     *
     * @param message the detail message
     * @param format  the format arguments
     * @return a new {@link MismatchedArgumentsException} with the specified detail message
     */
    public static @NotNull MismatchedArgumentsException from(@NotNull String message, @NotNull Object... format) {
        return new MismatchedArgumentsException(message.formatted(format));
    }
}
