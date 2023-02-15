package fade.mirror;

import org.jetbrains.annotations.NotNull;

public interface Invokable<T> {

    @NotNull T invoke(@NotNull Object... arguments);
}
