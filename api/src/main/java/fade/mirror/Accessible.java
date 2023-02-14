package fade.mirror;

import fade.mirror.exception.InaccessibleException;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Accessor<T> {

    boolean isAccessible();

    @NotNull Accessor<T> makeAccessible();

    @NotNull
    default Accessor<T> requireAccessible() {
        return this.requireAccessible(InaccessibleException::create);
    }

    @NotNull Accessor<T> requireAccessible(@NotNull Supplier<? extends RuntimeException> exception);

    @NotNull Accessor<T> ifAccessible(@NotNull Consumer<T> consumer);

    @NotNull Accessor<T> ifNotAccessible(@NotNull Consumer<T> consumer);
}
