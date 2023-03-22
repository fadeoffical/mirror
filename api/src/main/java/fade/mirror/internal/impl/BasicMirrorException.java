package fade.mirror.internal.impl;

import fade.mirror.MException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public final class BasicMirrorException<T extends Throwable> implements MException<T> {

    private final Class<T> type;

    @ApiStatus.Internal
    public static <T extends Throwable> BasicMirrorException<T> from(@NotNull Class<T> type) {
        return new BasicMirrorException<>(type);
    }

    private BasicMirrorException(@NotNull Class<T> type) {
        this.type = type;
    }

    @Override
    public @NotNull Class<T> getType() {
        return this.type;
    }

    @Override
    public void yeet() throws T {
        throw this.type.cast(new Throwable());
    }
}
