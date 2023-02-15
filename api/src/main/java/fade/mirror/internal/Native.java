package fade.mirror.internal;

import org.jetbrains.annotations.NotNull;

public interface Native<T> {

    @NotNull T getNativeReflectionObject();
}
