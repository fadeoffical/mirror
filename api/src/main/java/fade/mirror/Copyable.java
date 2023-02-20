package fade.mirror;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a mirror wrapper that can be copied.
 *
 * @param <T> The type to be returned by the methods; for chaining.
 * @author fade
 */
public interface Copyable<T> {

    /**
     * Copies the mirror wrapper.
     *
     * @return the copied mirror wrapper
     */
    @NotNull T copy();
}
