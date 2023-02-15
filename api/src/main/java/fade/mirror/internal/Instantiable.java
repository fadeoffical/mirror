package fade.mirror.internal;

import org.jetbrains.annotations.NotNull;

public interface Instantiable<T> {

    @NotNull T instantiate();
}
