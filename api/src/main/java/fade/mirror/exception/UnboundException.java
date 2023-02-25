package fade.mirror.exception;

import org.jetbrains.annotations.NotNull;

public final class UnboundException
        extends MirrorException {

    private UnboundException(String message) {
        super(message);
    }

    public static @NotNull UnboundException from(@NotNull String message, @NotNull Object... format) {
        return new UnboundException(message.formatted(format));
    }
}
