package fade.mirror.internal.impl;

import fade.mirror.MClass;
import fade.mirror.MMethod;
import fade.mirror.MParameter;
import fade.mirror.internal.exception.InaccessibleException;
import fade.mirror.internal.exception.InvocationException;
import fade.mirror.internal.exception.MismatchedArgumentsException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class MMethodImpl<T> implements MMethod<T> {

    private final Method method;
    private Object object;

    private MMethodImpl(@NotNull Method method) {
        this.method = method;
    }

    public static <T> MMethodImpl<T> from(@NotNull Method method) {
        return new MMethodImpl<>(method);
    }

    @Override
    public boolean isPublic() {
        return Modifier.isPublic(this.method.getModifiers());
    }

    @Override
    public boolean isProtected() {
        return Modifier.isProtected(this.method.getModifiers());
    }

    @Override
    public boolean isPackagePrivate() {
        return !this.isPublic() && !this.isProtected() && !this.isPrivate();
    }

    @Override
    public boolean isPrivate() {
        return Modifier.isPrivate(this.method.getModifiers());
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(this.method.getModifiers());
    }

    @Override
    public boolean isAccessible() {
        if (this.isStatic()) return this.method.canAccess(null);
        if (this.object == null) return false; // todo: throw exception

        return this.method.canAccess(this.object);
    }

    @Override
    public @NotNull MMethod<T> makeAccessible() {
        if (!this.isAccessible()) this.method.trySetAccessible();
        return this;
    }

    @Override
    public @NotNull MMethod<T> requireAccessible() {
        return this.requireAccessible(() -> InaccessibleException.from("Method is not accessible"));
    }

    @Override
    public @NotNull MMethod<T> requireAccessible(@NotNull Supplier<? extends RuntimeException> exception) {
        this.makeAccessible();
        if (!this.isAccessible()) throw exception.get();
        return this;
    }

    @Override
    public @NotNull MMethod<T> ifAccessible(@NotNull Consumer<MMethod<T>> consumer) {
        if (this.isAccessible()) consumer.accept(this);
        return this;
    }

    @Override
    public @NotNull MMethod<T> ifNotAccessible(@NotNull Consumer<MMethod<T>> consumer) {
        if (!this.isAccessible()) consumer.accept(this);
        return this;
    }

    @Override
    public @NotNull Stream<Annotation> getAnnotations() {
        return Arrays.stream(this.method.getAnnotations());
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
//        Set<Class<? extends Annotation>> annotationSet = new HashSet<>(Arrays.asList(annotations));
//        return this.getAnnotations().anyMatch(annotation -> annotationSet.contains(annotation.annotationType()));
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
        return this.method.getAnnotations().length;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @Nullable T invoke(@Nullable Object... arguments) {
        if (!this.isAccessible())
            throw InaccessibleException.from("Could not invoke method '%s' from '%s'; it is inaccessible", this.getName(), this.getDeclaringClass()
                    .getName());

        if (!this.invokableWith(arguments))
            throw MismatchedArgumentsException.from("Mismatched argument types for method '%s' from '%s'; provided=%s", this.getName(), this.getDeclaringClass()
                    .getName(), Arrays.toString(arguments));

        try {
            if (this.isStatic()) return (T) this.method.invoke(null, arguments);
            if (this.object == null) return null; // todo: throw exception
            return (T) this.method.invoke(this.object, arguments);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            throw InvocationException.from(exception, "Could not invoke method '%s' from '%s'", this.getName(), this.getDeclaringClass()
                    .getName());
        }
    }

    @Override
    public boolean invokableWith(@Nullable Object... arguments) {
        Class<?>[] argumentTypes = Arrays.stream(arguments)
                .map(object -> object == null ? null : object.getClass())
                .toArray(Class<?>[]::new);
        Class<?>[] parameterTypes = this.method.getParameterTypes();

        // copied and adapted from Arrays#equals
        if (parameterTypes == argumentTypes) return true;
        if (parameterTypes.length != argumentTypes.length) return false;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (argumentTypes[i] != null && !Objects.equals(parameterTypes[i], argumentTypes[i])) return false;
        }

        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Class<T> getReturnType() {
        return (Class<T>) this.method.getReturnType();
    }

    @Override
    public @NotNull String getName() {
        return this.method.getName();
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParameters() {
        return Arrays.stream(this.method.getParameters()).map(MParameterImpl::from);
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
        return this.getParameters()
                .filter(parameter -> parameter.getType().equals(type))
                .map(parameter -> (MParameter<C>) parameter);
    }

    @Override
    public @NotNull <C> Optional<MParameter<C>> getParameterOfType(@NotNull Class<C> type) {
        return this.getParametersOfType(type).findFirst();
    }

    @Override
    public @NotNull Stream<MParameter<?>> getParametersWithAnnotations(@NotNull Class<? extends Annotation>[] annotations) {
        return this.getParameters().filter(parameter -> parameter.isAnnotatedWith(annotations));
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
        return this.method.getParameterCount();
    }

    @Override
    public @NotNull MMethod<T> copy() {
        return MMethodImpl.from(this.method);
    }

    @Override
    public void bindToObject(@NotNull Object object) {
        this.object = object;
    }

    @Override
    public @NotNull MClass<?> getDeclaringClass() {
        return MClassImpl.from(this.method.getDeclaringClass());
    }
}
