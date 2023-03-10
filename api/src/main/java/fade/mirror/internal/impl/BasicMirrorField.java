package fade.mirror.internal.impl;

import fade.mirror.MClass;
import fade.mirror.MField;
import fade.mirror.Mirror;
import fade.mirror.exception.UnboundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
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
    private Object object;


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
    public static <T> @NotNull BasicMirrorField<T> from(@NotNull Field field) {
        return new BasicMirrorField<>(field);
    }

    @Override
    public boolean isPublic() {
        return Modifier.isPublic(this.field.getModifiers());
    }

    @Override
    public boolean isProtected() {
        return Modifier.isProtected(this.field.getModifiers());
    }

    @Override
    public boolean isPackagePrivate() {
        return !this.isPublic() && !this.isProtected() && !this.isPrivate();
    }

    @Override
    public boolean isPrivate() {
        return Modifier.isPrivate(this.field.getModifiers());
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(this.field.getModifiers());
    }

    @Override
    public boolean isAccessible() {
        if (this.isStatic()) return this.field.canAccess(null);
        if (this.object == null) throw UnboundException.from("Method is not static and no object is bound.");

        return this.field.canAccess(this.object);
    }

    @Override
    public @NotNull MField<T> makeAccessible() {
        if (!this.isAccessible()) this.field.trySetAccessible();
        return this;
    }

    @Override
    public @NotNull Stream<Annotation> getAnnotations() {
        return Arrays.stream(this.field.getAnnotations());
    }

    @Override
    public @NotNull Stream<Annotation> getAnnotations(@NotNull Predicate<Annotation> filter) {
        return this.getAnnotations().filter(filter);
    }

    @Override
    public @NotNull Optional<Annotation> getAnnotation(@NotNull Predicate<Annotation> filter) {
        return this.getAnnotations(filter).findFirst();
    }

    @Override
    public boolean isAnnotatedWith(@NotNull Class<? extends Annotation>[] annotations) {
        return Arrays.stream(annotations).anyMatch(this::isAnnotatedWith);
    }

    @Override
    public boolean isAnnotatedWith(@NotNull Class<? extends Annotation> annotation) {
        return this.getAnnotations().map(Annotation::annotationType).anyMatch(annotation::equals);
    }

    @Override
    public @NotNull <C extends Annotation> Optional<C> getAnnotationOfType(@NotNull Class<C> type) {
        return this.getAnnotations().filter(type::isInstance).map(type::cast).findFirst();
    }

    @Override
    public boolean isAnnotated() {
        return this.getAnnotationCount() > 0;
    }

    @Override
    public int getAnnotationCount() {
        return this.field.getAnnotations().length;
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
    public @NotNull MField<T> bindToObject(@NotNull Object object) {
        this.object = object;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Class<T> getType() {
        return (Class<T>) this.field.getType();
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Optional<T> getValue() {
        this.requireAccessible();
        try {
            return Optional.ofNullable((T) this.field.get(this.object == null ? null : this.object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Optional<T> getValue(@NotNull Object object) {
        this.requireAccessible();
        try {
            return Optional.ofNullable((T) this.field.get(object));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull MField<T> setValue(@Nullable T value) {
        this.requireAccessible();
        try {
            this.field.set(this.object == null ? null : this.object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public @NotNull MField<T> setValue(@NotNull Object object, @Nullable T value) {
        this.requireAccessible();
        try {
            this.field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    @Override
    public boolean hasValue() {
        this.requireAccessible();
        try {
            return this.field.get(this.object == null ? null : this.object) != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasValue(@NotNull Object object) {
        this.requireAccessible();
        try {
            return this.field.get(object) != null;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
