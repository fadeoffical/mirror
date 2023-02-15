package fade.mirror.internal;

import org.jetbrains.annotations.NotNull;

public interface Invokable<T> {

    @NotNull T invoke(@NotNull Object... arguments);
}
