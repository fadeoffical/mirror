package fade.mirror.exception;

import org.jetbrains.annotations.NotNull;

public final class InaccessibleException extends MirrorException {

    private InaccessibleException() {
        super();
    }

    public static @NotNull InaccessibleException create() {
        return new InaccessibleException();
    }
}
