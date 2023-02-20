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
     *
     * @return the field type.
     */
    @NotNull Class<T> getType();

    /**
     * Gets the value of the field. If the field is static, the value will be the same for all instances of the class.
     * Otherwise, the value will be dependent on the instance.
     * <p>
     * If the field is bound to an instance, the value will be the value of the field in that instance.
     * </p>
     *
     * @return the field value.
     */
    @NotNull Optional<T> getValue();

    /**
     * Sets the value of the field.
     * <p>
     * If the field is bound to an instance, the value will be the value of the field in that instance.
     * </p>
     *
     * @param value the new value.
     * @return this field.
     */
    @NotNull MField<T> setValue(@Nullable T value);

    /**
     * Gets the value of the field in the given instance.
     *
     * @param object the instance.
     * @return the field value.
     */
    @NotNull Optional<T> getValue(@NotNull Object object);

    /**
     * Sets the value of the field in the given instance.
     *
     * @param object the instance.
     * @param value  the new value.
     * @return this field.
     */
    @NotNull MField<T> setValue(@NotNull Object object, @Nullable T value);

    /**
     * Checks if the field has a value. A value is considered to be present if it is not {@code null}.
     *
     * @return {@code true} if the field has a value, {@code false} otherwise.
     */
    boolean hasValue();

    /**
     * Checks if the field has a value in the given instance. A value is considered to be present if it is not
     * {@code null}.
     *
     * @param object the instance.
     * @return {@code true} if the field has a value, {@code false} otherwise.
     */
    boolean hasValue(@NotNull Object object);
}
