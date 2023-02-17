package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Accessible<T> {

    boolean isAccessible();

    @NotNull T makeAccessible();

    @NotNull T requireAccessible();

    @NotNull T requireAccessible(@NotNull Supplier<? extends RuntimeException> exception);

    @NotNull T ifAccessible(@NotNull Consumer<T> consumer);

    @NotNull T ifNotAccessible(@NotNull Consumer<T> consumer);
}
