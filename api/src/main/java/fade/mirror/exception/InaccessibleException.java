package fade.mirror.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when a mirror object of type {@link fade.mirror.Accessible} is not accessible.
 *
 * @author fade
 */
public final class InaccessibleException extends MirrorException {

    /**
     * Constructs an {@code InaccessibleException} with the specified detail message.
     *
     * @param message the detail message
     */
    private InaccessibleException(@NotNull String message) {
        super(message);
    }

    /**
     * Constructs an {@code InaccessibleException} with the specified detail message.
     *
     * @param message the detail message
     * @param format  the format of the detail message
     * @return an {@code InaccessibleException} with the specified detail message
     */
    public static @NotNull InaccessibleException from(@NotNull String message, @NotNull Object... format) {
        return new InaccessibleException(message.formatted(format));
    }
}
