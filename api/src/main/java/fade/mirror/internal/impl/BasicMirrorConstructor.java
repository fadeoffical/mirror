package fade.mirror.internal.impl;

import fade.mirror.MClass;
import fade.mirror.MConstructor;
import fade.mirror.MParameter;
import fade.mirror.Mirror;
import fade.mirror.exception.InaccessibleException;
import fade.mirror.exception.InvocationException;
import fade.mirror.exception.MismatchedArgumentsException;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A basic implementation of {@link MConstructor}.
 *
 * @param <Type> The type of the constructor's declaring class.
 * @author fade
 */
public final class BasicMirrorConstructor<Type>
        implements MConstructor<Type> {

    private final Constructor<Type> constructor;

    private BasicMirrorConstructor(@NotNull Constructor<Type> constructor) {
        this.constructor = constructor;
    }

    /**
     * Creates a new {@link MConstructor} from the given {@link Constructor}. This method should not be used directly.
     * Instead, use {@link Mirror#mirror(Constructor)}.
     *
     * @param constructor The constructor to create the mirror from.
     * @param <Type>         The type of the constructor's declaring class.
     * @return The created mirror.
     */
    @ApiStatus.Internal
    @Contract(value = "_ -> new", pure = true)
    public static <Type> @NotNull MConstructor<Type> from(@NotNull Constructor<Type> constructor) {
        return new BasicMirrorConstructor<>(constructor);
    }

    @Override
    public @NotNull Type invokeWithInstance(@Nullable Object instance, @Nullable Object... arguments) {
        this.requireAccessible(); // todo: is the check below necessary?

        if (!this.isAccessible())
            throw InaccessibleException.from("Could not invoke constructor '%s' from '%s'; it is inaccessible", this.getPrettyConstructorRepresentation(), this.getDeclaringClass()
                    .getName());

        if (!this.isInvokableWith(arguments))
            throw MismatchedArgumentsException.from("Mismatched argument types for constructor '%s' from '%s'; provided=%s", this.getPrettyConstructorRepresentation(), this.getDeclaringClass()
                    .getName(), Arrays.toString(arguments));

        try {
            return this.constructor.newInstance(arguments);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw InvocationException.from(exception, "Could not invoke constructor '%s' from '%s'", this.getPrettyConstructorRepresentation(), this.getDeclaringClass()
                    .getName());
        }
    }

    private @NotNull String getPrettyConstructorRepresentation() {
        StringBuilder builder = new StringBuilder().append(this.constructor.getName()).append('(');
        for (Class<?> type : this.constructor.getParameterTypes()) builder.append(type.getSimpleName()).append(", ");
        return builder.append(")").toString();
    }

    @Override
    public @NotNull MClass<Type> getDeclaringClass() {
        return BasicMirrorClass.from(this.constructor.getDeclaringClass());
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParameters() {
        return Arrays.stream(this.constructor.getParameters()).map(BasicMirrorParameter::from);
    }

    @Override
    public @NotNull Class<Type> getReturnType() {
        return this.getDeclaringClass().getRawClass();
    }

    @Override
    public int getParameterCount() {
        return this.constructor.getParameterCount();
    }

    public Class<?>[] getParameterTypes() {
        return this.constructor.getParameterTypes();
    }

    @Override
    public @NotNull Constructor<Type> getRawConstructor() {
        return this.constructor;
    }

    @Override
    public int getModifiers() {
        return this.constructor.getModifiers();
    }

    @Override
    public @NotNull MConstructor<Type> makeAccessible(@Nullable Object instance) {
        if (!this.isAccessible(instance)) {
            this.constructor.trySetAccessible();
        }
        return this;
    }

    @Override
    public boolean isAccessible(@Nullable Object instance) {
        if (instance != null)
            throw new IllegalArgumentException("Cannot check accessibility of a constructor with an instance; use #isAccessible() instead");

        return this.constructor.canAccess(null);
    }

    @Override
    public @NotNull Stream<Annotation> getAnnotations() {
        return Arrays.stream(this.constructor.getAnnotations());
    }

    @Override
    public int getAnnotationCount() {
        return this.constructor.getAnnotations().length;
    }
}
