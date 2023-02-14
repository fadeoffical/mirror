package fade.mirror;

import fade.mirror.exception.InaccessibleException;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Accessible<T> {

    boolean isAccessible();

    @NotNull Accessible<T> makeAccessible();

    @NotNull
    default Accessible<T> requireAccessible() {
        return this.requireAccessible(InaccessibleException::create);
    }

    @NotNull Accessible<T> requireAccessible(@NotNull Supplier<? extends RuntimeException> exception);

    @NotNull Accessible<T> ifAccessible(@NotNull Consumer<T> consumer);

    @NotNull Accessible<T> ifNotAccessible(@NotNull Consumer<T> consumer);
}
