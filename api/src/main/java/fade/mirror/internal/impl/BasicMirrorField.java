package fade.mirror.internal.impl;

import fade.mirror.MClass;
import fade.mirror.MField;
import fade.mirror.Mirror;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A basic implementation of {@link MField}.
 *
 * @param <T> The type of the field.
 * @author fade
 */
public final class BasicMirrorField<T>
        implements MField<T> {

    private final Field field;

    @ApiStatus.Internal
    private BasicMirrorField(@NotNull Field field) {
        this.field = field;
    }

    /**
     * Creates a new {@link BasicMirrorField} instance. This method should not be used directly. Instead, use
     * {@link Mirror#mirror(Field)}.
     *
     * @param field The field to create the mirror from.
     * @param <T>   The type of the field.
     * @return The created mirror.
     */
    @ApiStatus.Internal
    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull BasicMirrorField<T> from(@NotNull Field field) {
        return new BasicMirrorField<>(field);
    }

    @Override
    public @NotNull MClass<?> getDeclaringClass() {
        return BasicMirrorClass.from(this.field.getDeclaringClass());
    }

    @Override
    public @NotNull String getName() {
        return this.field.getName();
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Class<T> getType() {
        return (Class<T>) this.field.getType();
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Optional<T> getValue(@Nullable Object instance) {
        this.requireAccessible(instance);
        try {
            return Optional.ofNullable((T) this.field.get(instance));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull MField<T> setValue(@Nullable Object instance, @Nullable T value) {
        this.requireAccessible(instance);
        try {
            this.field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public boolean hasValue(@Nullable Object object) {
        this.requireAccessible();
        try {
            return this.field.get(object) != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getModifiers() {
        return this.field.getModifiers();
    }

    @Override
    public @NotNull MField<T> makeAccessible(@Nullable Object instance) {
        if (!this.isAccessible(instance))
            this.field.trySetAccessible();

        return this;
    }

    @Override
    public boolean isAccessible(@Nullable Object instance) {
        return this.field.canAccess(instance);
    }

    @Override
    public @NotNull Stream<Annotation> getAnnotations() {
        return Arrays.stream(this.field.getAnnotations());
    }

    @Override
    public int getAnnotationCount() {
        return this.field.getAnnotations().length;
    }

}
