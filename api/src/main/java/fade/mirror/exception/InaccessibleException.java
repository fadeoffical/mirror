package fade.mirror.exception;

import org.jetbrains.annotations.NotNull;

public final class InaccessibleException extends MirrorException {

    private InaccessibleException(@NotNull String message) {
        super(message);
    }

    public static @NotNull InaccessibleException from(@NotNull String message, @NotNull Object... format) {
        return new InaccessibleException(message.formatted(format));
    }
}
