package fade.mirror;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a mirror wrapper that can be copied.
 *
 * @param <T> The type to be returned by the methods; for chaining.
 * @author fade
 */
public interface Copyable<T> {


    @Contract(pure = true)
    @NotNull T copy();
}
