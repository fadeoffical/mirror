package fade.mirror;

import org.jetbrains.annotations.Nullable;

public interface Invokable<T> {

    @Nullable T invoke(@Nullable Object... arguments);

    boolean invokableWith(@Nullable Object... arguments);
}
