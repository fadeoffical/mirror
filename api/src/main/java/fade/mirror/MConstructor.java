package fade.mirror;

import fade.mirror.exception.InaccessibleException;
import fade.mirror.exception.InvocationException;
import fade.mirror.exception.MismatchedArgumentsException;
import fade.mirror.internal.*;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class MConstructor<T> implements Typed<T>, Accessible<Constructor<T>>, Parameterized, Annotated, Invokable<T> {

    private final Constructor<T> constructor;

    MConstructor(@NotNull Constructor<T> constructor) {
        this.constructor = constructor;
    }

    @Override
    public boolean isAccessible() {
        return this.constructor.canAccess(null);
    }

    @Override
    public @NotNull MConstructor<T> makeAccessible() {
        if (!this.isAccessible()) {
            this.constructor.trySetAccessible();
        }
        return this;
    }

    @Override
    public @NotNull MConstructor<T> requireAccessible() {
        return (MConstructor<T>) Accessible.super.requireAccessible();
    }

    @Override
    public @NotNull MConstructor<T> requireAccessible(@NotNull Supplier<? extends RuntimeException> exception) {
        this.makeAccessible();
        if (!this.isAccessible()) throw exception.get();

        return this;
    }

    @Override
    public @NotNull MConstructor<T> ifAccessible(@NotNull Consumer<Constructor<T>> consumer) {
        if (this.isAccessible()) consumer.accept(this.constructor);
        return this;
    }

    @Override
    public @NotNull MConstructor<T> ifNotAccessible(@NotNull Consumer<Constructor<T>> consumer) {
        if (!this.isAccessible()) consumer.accept(this.constructor);
        return this;
    }

    @Override
    public @NotNull T invoke(@NotNull Object... arguments) {
        if (!this.isAccessible())
            throw InaccessibleException.from("Could not invoke constructor '%s' from '%s'; it is inaccessible", this.getPrettyConstructorRepresentation(), this.getDeclaringClass()
                    .getName());

        Class<?>[] argumentTypes = Arrays.stream(arguments).map(Object::getClass).toArray(Class<?>[]::new);
        if (!Arrays.equals(this.constructor.getParameterTypes(), argumentTypes))
            throw MismatchedArgumentsException.from("Mismatched argument types for constructor '%s' from '%s'", this.getPrettyConstructorRepresentation(), this.getDeclaringClass()
                    .getName());

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
    public @NotNull Stream<MParameter<?>> getParameters() {
        return null;
    }

    @Override
    public @NotNull <T> Stream<MParameter<T>> getParametersWithType(@NotNull Class<T> type) {
        return null;
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParametersWithAnnotation(@NotNull Class<? extends Annotation> annotation) {
        return null;
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParametersWithAnnotations(@NotNull Class<? extends Annotation>[] annotations) {
        return null;
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParametersWithoutAnnotation(@NotNull Class<? extends Annotation> annotation) {
        return null;
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParametersWithoutAnnotations(@NotNull Class<? extends Annotation>[] annotations) {
        return null;
    }

    @Override
    public boolean areParametersEqual(@NotNull Class<?>[] types) {
        return Arrays.equals(this.constructor.getParameterTypes(), types);
    }

    @Override
    public boolean isParameterPresent(@NotNull Class<?>[] type) {
        return false;
    }

    @Override
    public boolean isParameterAbsent(@NotNull Class<?>[] type) {
        return false;
    }

    @Override
    public boolean areAnnotationsPresent(@NotNull Annotation[] annotations) {
        return Arrays.stream(annotations).anyMatch(annotation -> !this.isAnnotationPresent(annotation));
    }

    @Override
    public boolean isAnnotationPresent(@NotNull Annotation annotation) {
        return this.constructor.isAnnotationPresent(annotation.annotationType());
    }

    public @NotNull Class<T> getDeclaringClass() {
        return this.constructor.getDeclaringClass();
    }

    @Override
    public @NotNull Class<T> getType() {
        return null;
    }
}
