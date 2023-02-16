package fade.mirror.internal;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Invokable<T> {

    @NotNull T invoke(@Nullable Object... arguments);

    boolean invokableWith(@Nullable Object... arguments);
}
