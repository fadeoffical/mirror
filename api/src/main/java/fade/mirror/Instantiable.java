package fade.mirror;

import org.jetbrains.annotations.NotNull;

public interface Instantiable<T> {

    @NotNull T instantiate();
}
