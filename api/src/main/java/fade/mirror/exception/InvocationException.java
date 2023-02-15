package fade.mirror.exception;

import org.jetbrains.annotations.NotNull;

public final class InvocationException extends MirrorException {

    private InvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InvocationException from(@NotNull Throwable cause, @NotNull String message, @NotNull Object... format) {
        return new InvocationException(message.formatted(format), cause);
    }
}
