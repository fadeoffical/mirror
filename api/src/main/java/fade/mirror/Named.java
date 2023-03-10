package fade.mirror;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a reflection object that has a name. This is a common interface for {@link MClass}, {@link MField},
 * {@link MMethod} and {@link MParameter}.
 *
 * @author fade
 */
public interface Named {

    /**
     * Returns the name of the reflection object.
     *
     * @return the name.
     */
    @NotNull String getName();

    /**
     * Checks if the name of the reflection object is equal to the given name.
     *
     * @param name the name to check.
     * @return {@code true} if the name is equal, {@code false} otherwise.
     */
    default boolean isNameEqualTo(@NotNull String name) {
        return this.getName().equals(name);
    }
}
