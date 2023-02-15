package fade.mirror.internal;

import org.jetbrains.annotations.NotNull;

public interface Typed<T> {

    @NotNull Class<T> getType();
}
