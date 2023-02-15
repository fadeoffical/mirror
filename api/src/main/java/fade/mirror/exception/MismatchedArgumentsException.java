package fade.mirror.exception;

import org.jetbrains.annotations.NotNull;

public final class MismatchedArgumentsException extends MirrorException {

    private MismatchedArgumentsException(@NotNull String message) {
        super(message);
    }

    public static @NotNull MismatchedArgumentsException from(@NotNull String message, @NotNull Object... format) {
        return new MismatchedArgumentsException(message.formatted(format));
    }
}
