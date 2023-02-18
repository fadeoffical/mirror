package fade.mirror;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a mirror wrapper that can be copied.
 *
 * @param <T> the type of the mirror wrapper
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
