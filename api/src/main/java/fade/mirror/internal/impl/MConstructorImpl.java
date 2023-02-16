package fade.mirror.internal.impl;

import fade.mirror.MClass;
import fade.mirror.MConstructor;
import fade.mirror.MParameter;
import fade.mirror.exception.InaccessibleException;
import fade.mirror.exception.InvocationException;
import fade.mirror.exception.MismatchedArgumentsException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class MConstructorImpl<T> implements MConstructor<T> {

    private final Constructor<T> constructor;

    private MConstructorImpl(@NotNull Constructor<T> constructor) {
        this.constructor = constructor;
    }

    public static <T> MConstructor<T> from(@NotNull Constructor<T> constructor) {
        return new MConstructorImpl<>(constructor);
    }

    @Override
    public boolean isAccessible() {
        return this.constructor.canAccess(null);
    }

    @Override
    public @NotNull MConstructor<T> requireAccessible() {
        return this.requireAccessible(() -> InaccessibleException.from("Inaccessible"));
    }

    @Override
    public @NotNull MConstructor<T> makeAccessible() {
        if (!this.isAccessible()) {
            this.constructor.trySetAccessible();
        }
        return this;
    }

    @Override
    public @NotNull MConstructor<T> requireAccessible(@NotNull Supplier<? extends RuntimeException> exception) {
        this.makeAccessible();
        if (!this.isAccessible()) throw exception.get();
        return this;
    }

    @Override
    public @NotNull MConstructor<T> ifAccessible(@NotNull Consumer<MConstructor<T>> consumer) {
        if (this.isAccessible()) consumer.accept(this);
        return this;
    }

    @Override
    public @NotNull MConstructor<T> ifNotAccessible(@NotNull Consumer<MConstructor<T>> consumer) {
        if (!this.isAccessible()) consumer.accept(this);
        return this;
    }

    @Override
    public @NotNull T invoke(@NotNull Object... arguments) {
        if (!this.isAccessible())
            throw InaccessibleException.from("Could not invoke constructor '%s' from '%s'; it is inaccessible", this.getPrettyConstructorRepresentation(), this.getDeclaringClass()
                    .getName());

        if (!this.invokableWith(arguments))
            throw MismatchedArgumentsException.from("Mismatched argument types for constructor '%s' from '%s'", this.getPrettyConstructorRepresentation(), this.getDeclaringClass()
                    .getName());

        try {
            return this.constructor.newInstance(arguments);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw InvocationException.from(exception, "Could not invoke constructor '%s' from '%s'", this.getPrettyConstructorRepresentation(), this.getDeclaringClass()
                    .getName());
        }
    }

    @Override
    public boolean invokableWith(@NotNull Object... arguments) {
        Class<?>[] argumentTypes = Arrays.stream(arguments).map(Object::getClass).toArray(Class<?>[]::new);
        return Arrays.equals(this.constructor.getParameterTypes(), argumentTypes);
    }

    private @NotNull String getPrettyConstructorRepresentation() {
        StringBuilder builder = new StringBuilder().append(this.constructor.getName()).append('(');
        for (Class<?> type : this.constructor.getParameterTypes()) builder.append(type.getSimpleName()).append(", ");
        return builder.append(")").toString();
    }

    @Override
    public @NotNull Constructor<T> getRawConstructor() {
        return this.constructor;
    }

    @Override
    public @NotNull MClass<T> getDeclaringClass() {
        return MClassImpl.from(this.constructor.getDeclaringClass());
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
    public boolean isAnnotated() {
        return this.getAnnotationCount() > 0;
    }

    @Override
    public int getAnnotationCount() {
        return this.constructor.getAnnotations().length;
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParameters() {
        return Arrays.stream(this.constructor.getParameters()).map(MParameterImpl::from);
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
