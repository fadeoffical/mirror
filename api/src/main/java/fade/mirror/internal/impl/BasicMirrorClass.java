package fade.mirror.internal.impl;

import fade.mirror.*;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Basic implementation of {@link MClass}.
 *
 * @param <T> The type of the class.
 */
public final class BasicMirrorClass<T>
        implements MClass<T> {

    /**
     * The class.
     */
    private final @NotNull Class<T> clazz;

    /**
     * Creates a new {@link BasicMirrorClass} instance.
     *
     * @param clazz The class.
     */
    private BasicMirrorClass(@NotNull Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * Creates a new {@link BasicMirrorClass} instance. This method should not be used directly. Use
     * {@link Mirror#mirror(Class)} instead.
     *
     * @param clazz The class.
     * @param <T>   The type of the class.
     * @return The new {@link BasicMirrorClass} instance.
     */
    public static <T> @NotNull BasicMirrorClass<T> from(@NotNull Class<T> clazz) {
        return new BasicMirrorClass<>(clazz);
    }

    @Override
    public @NotNull Class<T> getRawClass() {
        return this.clazz;
    }

    @Override
    public @NotNull Optional<MClass<?>> getSuperclass() {
        Class<? super T> superclass = this.clazz.getSuperclass();
        return Optional.ofNullable(this.hasSuperclass() ? from(superclass) : null);
    }

    @Override
    public @NotNull <C> Optional<MClass<C>> getSuperclassUntil(@NotNull Predicate<MClass<C>> filter) {
        return this.getSuperclassUntil(filter, false);
    }

    @Override
    public @NotNull <C> Optional<MClass<C>> getSuperclassUntilIncludingSelf(@NotNull Predicate<MClass<C>> filter) {
        return this.getSuperclassUntil(filter, true);
    }

    @SuppressWarnings("unchecked")
    private <C> @NotNull Optional<MClass<C>> getSuperclassUntil(@NotNull Predicate<MClass<C>> filter, boolean includeSelf) {
        Optional<MClass<?>> optionalClass = includeSelf ? Optional.of(this) : this.getSuperclass();

        do {
            if (optionalClass.isEmpty())
                return Optional.empty();

            MClass<C> clazz = (MClass<C>) optionalClass.get();

            if (filter.test(clazz))
                return Optional.of(clazz);

            optionalClass = clazz.getSuperclass();
        } while (optionalClass.isPresent() && optionalClass.get().hasSuperclass());
        return Optional.empty();
    }

    @Override
    public boolean hasSuperclass() {
        return this.clazz.getSuperclass() != null;
    }

    @Override
    public @NotNull Stream<MConstructor<T>> getConstructors() {
        return this.getRawConstructors().map(BasicMirrorConstructor::from);
    }

    @Override
    public @NotNull Stream<MConstructor<T>> getConstructors(@NotNull Predicate<MConstructor<T>> filter) {
        return this.getConstructors().filter(filter);
    }

    @Override
    public @NotNull Optional<MConstructor<T>> getConstructor() {
        return this.getConstructors().findFirst();
    }

    @Override
    public @NotNull Optional<MConstructor<T>> getConstructor(@NotNull Predicate<MConstructor<T>> filter) {
        return this.getConstructors(filter).findFirst();
    }

    @Override
    public @NotNull Optional<MConstructor<T>> getConstructorWithTypes(@NotNull Class<?>... types) {
        return this.getConstructor(constructor -> Arrays.equals(constructor.getRawConstructor()
                .getParameterTypes(), types));
    }

    @Override
    public int getConstructorCount() {
        return this.clazz.getConstructors().length;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Stream<Constructor<T>> getRawConstructors() {
        return Arrays.stream(this.clazz.getConstructors()).map(constructor -> (Constructor<T>) constructor);
    }

    @Override
    public @NotNull Stream<MField<?>> getFields() {
        return this.getRawFields().map(BasicMirrorField::from);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <F> @NotNull Stream<MField<F>> getFields(@NotNull Predicate<MField<F>> filter) {
        return this.getFields().map(field -> (MField<F>) field).filter(filter);
    }

    @Override
    public <F> @NotNull Optional<MField<F>> getField(@NotNull Predicate<MField<F>> filter) {
        return this.getFields(filter).findFirst();
    }

    @Override
    public boolean hasFields() {
        return this.getFieldCount() > 0;
    }

    @Override
    public int getFieldCount() {
        return this.clazz.getDeclaredFields().length;
    }

    @Override
    public @NotNull Stream<Field> getRawFields() {
        return Arrays.stream(this.clazz.getDeclaredFields());
    }

    @Override
    public @NotNull Stream<MMethod<?>> getMethods() {
        return this.getRawMethods().map(BasicMirrorMethod::from);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <F> @NotNull Stream<MMethod<F>> getMethods(@NotNull Predicate<MMethod<F>> filter) {
        return this.getMethods().map(method -> (MMethod<F>) method).filter(filter);
    }

    @Override
    public <F> @NotNull Optional<MMethod<F>> getMethod(@NotNull Predicate<MMethod<F>> filter) {
        return this.getMethods(filter).findFirst();
    }

    @Override
    public boolean hasMethods() {
        return this.getMethodCount() > 0;
    }

    @Override
    public int getMethodCount() {
        return this.clazz.getDeclaredMethods().length;
    }

    @Override
    public @NotNull Stream<Method> getRawMethods() {
        return Arrays.stream(this.clazz.getDeclaredMethods());
    }

    @Override
    public @NotNull String getSimpleName() {
        return this.clazz.getSimpleName();
    }

    @Override
    public @NotNull String getCanonicalName() {
        return this.clazz.getCanonicalName();
    }

    @Override
    public @NotNull Stream<Annotation> getAnnotations() {
        return Arrays.stream(this.clazz.getAnnotations());
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
        return this.getAnnotations()
                .map(Annotation::annotationType)
                .filter(type::equals)
                .map(type::cast)
                .findFirst(); // todo: check if this works
    }

    @Override
    public boolean isAnnotated() {
        return this.getAnnotationCount() > 0;
    }

    @Override
    public int getAnnotationCount() {
        return this.clazz.getAnnotations().length;
    }

    @Override
    public @NotNull String getName() {
        return this.clazz.getName();
    }
}
