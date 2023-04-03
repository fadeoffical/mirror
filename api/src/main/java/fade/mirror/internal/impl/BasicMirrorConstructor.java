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
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A basic implementation of {@link MConstructor}.
 *
 * @param <T> The type of the constructor's declaring class.
 * @author fade
 */
public final class BasicMirrorConstructor<T>
        implements MConstructor<T> {

    private final Constructor<T> constructor;

    private BasicMirrorConstructor(@NotNull Constructor<T> constructor) {
        this.constructor = constructor;
    }

    /**
     * Creates a new {@link MConstructor} from the given {@link Constructor}. This method should not be used directly.
     * Instead, use {@link Mirror#mirror(Constructor)}.
     *
     * @param constructor The constructor to create the mirror from.
     * @param <T>         The type of the constructor's declaring class.
     * @return The created mirror.
     */
    @ApiStatus.Internal
    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull MConstructor<T> from(@NotNull Constructor<T> constructor) {
        return new BasicMirrorConstructor<>(constructor);
    }

    @Override
    public @NotNull T invokeWithInstance(@Nullable Object instance, @Nullable Object... arguments) {
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

    public Class<?>[] getParameterTypes() {
        return this.constructor.getParameterTypes();
    }

    @Override
    public @NotNull Class<T> getReturnType() {
        return this.getDeclaringClass().getRawClass();
    }

    private @NotNull String getPrettyConstructorRepresentation() {
        StringBuilder builder = new StringBuilder().append(this.constructor.getName()).append('(');
        for (Class<?> type : this.constructor.getParameterTypes()) builder.append(type.getSimpleName()).append(", ");
        return builder.append(")").toString();
    }

    @Override
    public @NotNull MClass<T> getDeclaringClass() {
        return BasicMirrorClass.from(this.constructor.getDeclaringClass());
    }

    @Override
    public @NotNull Constructor<T> getRawConstructor() {
        return this.constructor;
    }

    @Override
    public boolean isPublic() {
        return Modifier.isPublic(this.constructor.getModifiers());
    }

    @Override
    public boolean isProtected() {
        return Modifier.isProtected(this.constructor.getModifiers());
    }

    @Override
    public boolean isPackagePrivate() {
        return !this.isPublic() && !this.isProtected() && !this.isPrivate();
    }

    @Override
    public boolean isPrivate() {
        return Modifier.isPrivate(this.constructor.getModifiers());
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(this.constructor.getModifiers());
    }

    @Override
    public @NotNull MConstructor<T> makeAccessible(@Nullable Object instance) {
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
    public @NotNull Stream<Annotation> getAnnotations(@NotNull Predicate<Annotation> filter) {
        return this.getAnnotations().filter(filter);
    }

    @Override
    public @NotNull Optional<Annotation> getAnnotation(@NotNull Predicate<Annotation> filter) {
        return this.getAnnotations(filter).findFirst();
    }

    @Override
    public boolean isAnnotatedWith(@NotNull Class<? extends Annotation>[] annotations) {
        Set<Class<? extends Annotation>> annotationList = Set.of(annotations);
        return this.getAnnotations().map(Annotation::annotationType).anyMatch(annotationList::contains);
    }

    @Override
    public boolean isAnnotatedWith(@NotNull Class<? extends Annotation> annotation) {
        return this.getAnnotations().map(Annotation::annotationType).anyMatch(annotation::equals);
    }

    @Override
    public <C extends Annotation> @NotNull Optional<C> getAnnotationOfType(@NotNull Class<C> type) {
        return this.getAnnotations().filter(type::isInstance).map(type::cast).findFirst();
    }

    @Override
    public boolean isAnnotated() {
        return this.getAnnotationCount() > 0;
    }

    @Override
    public int getAnnotationCount() {
        return this.constructor.getAnnotations().length;
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParameters() {
        return Arrays.stream(this.constructor.getParameters()).map(BasicMirrorParameter::from);
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParameters(@NotNull Predicate<MParameter<?>> filter) {
        return this.getParameters().filter(filter);
    }

    @Override
    public @NotNull Optional<MParameter<?>> getParameter(@NotNull Predicate<MParameter<?>> filter) {
        return this.getParameters(filter).findFirst();
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <C> Stream<MParameter<C>> getParametersOfType(@NotNull Class<C> type) {
        return this.getParameters(parameter -> parameter.getType().equals(type))
                .map(parameter -> (MParameter<C>) parameter);
    }

    @Override
    public @NotNull <C> Optional<MParameter<C>> getParameterOfType(@NotNull Class<C> type) {
        return this.getParametersOfType(type).findFirst();
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParametersWithAnnotations(@NotNull Class<? extends Annotation>[] annotations) {
        return this.getParameters(parameter -> parameter.isAnnotatedWith(annotations));
    }

    @Override
    public @NotNull Optional<MParameter<?>> getParameterWithAnnotations(@NotNull Class<? extends Annotation>[] annotations) {
        return this.getParametersWithAnnotations(annotations).findFirst();
    }

    @Override
    public boolean hasParameters() {
        return this.getParameterCount() > 0;
    }

    @Override
    public int getParameterCount() {
        return this.constructor.getParameterCount();
    }
}
