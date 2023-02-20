package fade.mirror;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents a field inside a {@link MClass}. A field has a name, a type and a value. It may also have annotations.
 *
 * @param <T> the type of the class.
 * @author fade
 */

public interface MField<T>
        extends Accessible<MField<T>>, InstanceBindable<MField<T>>, Annotated, Named, Declared {

    /**
     * Gets the type of the field.
     * @return the field type.
     */
    @NotNull Class<T> getType();

    @NotNull Optional<T> getValue();

    @NotNull Optional<T> getValue(@NotNull Object object);

    @NotNull MField<T> setValue(@Nullable T value);

    @NotNull MField<T> setValue(@NotNull Object object, @Nullable T value);

    boolean hasValue();

    boolean hasValue(@NotNull Object object);
}
