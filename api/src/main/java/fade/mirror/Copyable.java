package fade.mirror;

import org.jetbrains.annotations.NotNull;

public interface Copyable<T> {

    @NotNull T copy();
}
