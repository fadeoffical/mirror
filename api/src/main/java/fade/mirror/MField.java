package fade.mirror;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents a field inside a {@link MClass}. A field has a name, a type and a value. It may also have annotations.
 *
 * @param <Type> the type of the class.
 * @author fade
 */

public interface MField<Type>
        extends Accessible<MField<Type>>, Annotated, Named, Declared {

    /**
     * Gets the type of the field.
     *
     * @return the field type.
     */
    @Contract(pure = true)
    @NotNull Class<Type> getType();

    /**
     * Gets the value of the field. If the field is static, the value will be the same for all instances of the class.
     * Otherwise, the value will be dependent on the instance.
     * <p>
     * If the field is bound to an instance, the value will be the value of the field in that instance.
     * </p>
     *
     * @return the field value.
     */
    @Contract(pure = true)
    default @NotNull Optional<Type> getValue() {
        return this.getValue(null);
    }

    /**
     * Gets the value of the field in the given instance.
     *
     * @param instance the instance.
     * @return the field value.
     */
    @Contract(pure = true)
    @NotNull Optional<Type> getValue(@Nullable Object instance);

    /**
     * Sets the value of the field.
     * <p>
     * If the field is bound to an instance, the value will be the value of the field in that instance.
     * </p>
     *
     * @param value the new value.
     * @return this field.
     */
    default @NotNull MField<Type> setValue(@Nullable Type value) {
        return this.setValue(null, value);
    }

    /**
     * Sets the value of the field in the given instance.
     *
     * @param instance the instance.
     * @param value    the new value.
     * @return this field.
     */
    @NotNull MField<Type> setValue(@Nullable Object instance, @Nullable Type value);

    /**
     * Checks if the field has a value. A value is considered to be present if it is not {@code null}.
     *
     * @return {@code true} if the field has a value, {@code false} otherwise.
     */
    @Contract(pure = true)
    default boolean hasValue() {
        return this.hasValue(null);
    }

    /**
     * Checks if the field has a value in the given instance. A value is considered to be present if it is not
     * {@code null}.
     *
     * @param object the instance.
     * @return {@code true} if the field has a value, {@code false} otherwise.
     */
    @Contract(pure = true)
    boolean hasValue(@Nullable Object object);
}
