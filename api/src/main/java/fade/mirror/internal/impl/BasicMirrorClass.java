package fade.mirror.internal.impl;

import fade.mirror.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
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
    @ApiStatus.Internal
    private BasicMirrorClass(@NotNull Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public @NotNull Class<T> getRawClass() {
        return this.clazz;
    }

    @Override
    public @NotNull Stream<MClass<?>> getSuperclasses() {
        return this.getSuperclasses(IncludeSelf.No);
    }

    @Override
    public @NotNull Stream<MClass<?>> getSuperclasses(@NotNull IncludeSelf includeSelf) {
        List<MClass<?>> classes = new ArrayList<>(1);
        Optional<MClass<?>> optionalClass = includeSelf.include() ? Optional.of(this) : this.getSuperclass();

        do {
            if (optionalClass.isEmpty()) break;
            classes.add(optionalClass.get());
            optionalClass = optionalClass.get().getSuperclass();
        } while (optionalClass.isPresent() && optionalClass.get().hasSuperclass());

        return classes.stream();
    }

    @Override
    public @NotNull Optional<MClass<?>> getSuperclass() {
        Class<? super T> superclass = this.clazz.getSuperclass();
        return Optional.ofNullable(this.hasSuperclass() ? from(superclass) : null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Optional<MClass<T>> getSuperclassAsThis() {
        return this.getSuperclass().map(clazz -> (MClass<T>) clazz);
    }

    @Override
    public @NotNull <C> Optional<MClass<C>> getSuperclassUntil(@NotNull Predicate<MClass<C>> filter) {
        return this.getSuperclassUntil(filter, IncludeSelf.No);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <C> Optional<MClass<C>> getSuperclassUntil(@NotNull Predicate<MClass<C>> filter, @NotNull MClass.IncludeSelf includeSelf) {
        Optional<MClass<?>> optionalClass = includeSelf.include() ? Optional.of(this) : this.getSuperclass();

        do {
            if (optionalClass.isEmpty()) return Optional.empty();
            MClass<C> clazz = (MClass<C>) optionalClass.get();
            if (filter.test(clazz)) return Optional.of(clazz);
            optionalClass = clazz.getSuperclass();
        } while (optionalClass.isPresent() && optionalClass.get().hasSuperclass());
        return Optional.empty();
    }

    @Override
    public boolean hasSuperclass() {
        return this.clazz.getSuperclass() != null;
    }

    @Override
    public boolean isSuperclassOf(@NotNull MClass<?> clazz) {
        return clazz.isSuperclassOf(this.clazz);
    }

    @Override
    public boolean isSuperclassOf(@NotNull Class<?> clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }

    @Override
    public <O extends T> @NotNull T cast(@NotNull O object) {
        if (!this.isSuperclassOf(object.getClass()))
            // todo: replace with custom exception
            throw new IllegalArgumentException("The given superclass is not a superclass of this class.");

        // the operation is safe because of the check above, but it's still inherently unsafe
        return this.unsafeCast(object);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull T unsafeCast(@NotNull Object object) {
        return (T) object;
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
    public @NotNull Stream<MField<?>> getFields(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include()) return this.getSuperclasses(IncludeSelf.Yes).flatMap(MClass::getFields);
        return this.getFields();
    }

    @Override
    public <F> @NotNull Stream<MField<F>> getFields(@NotNull Predicate<MField<F>> filter) {
        return this.getFields(filter, IncludeSuperclasses.No);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <F> Stream<MField<F>> getFields(@NotNull Predicate<MField<F>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include())
            return this.getSuperclasses(IncludeSelf.Yes).flatMap(clazz -> clazz.getFields(filter));
        return this.getFields().map(field -> (MField<F>) field).filter(filter);
    }

    @Override
    public <F> @NotNull Optional<MField<F>> getField(@NotNull Predicate<MField<F>> filter) {
        return this.getField(filter, IncludeSuperclasses.No);
    }

    @Override
    public @NotNull <F> Optional<MField<F>> getField(@NotNull Predicate<MField<F>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include()) return this.getFields(filter, includeSuperclasses).findFirst();
        return this.getFields(filter).findFirst();
    }

    @Override
    public boolean hasFields() {
        return this.hasFields(IncludeSuperclasses.No);
    }

    @Override
    public boolean hasFields(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include()) return this.getSuperclasses(IncludeSelf.Yes).anyMatch(MClass::hasFields);
        return this.getFieldCount() > 0;
    }

    @Override
    public int getFieldCount() {
        return this.getFieldCount(IncludeSuperclasses.No);
    }

    @Override
    public int getFieldCount(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include())
            return this.getSuperclasses(IncludeSelf.Yes).mapToInt(MClass::getFieldCount).sum();
        return this.clazz.getDeclaredFields().length;
    }

    @Override
    public @NotNull Stream<Field> getRawFields() {
        return this.getRawFields(IncludeSuperclasses.No);
    }

    @Override
    public @NotNull Stream<Field> getRawFields(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include()) return this.getSuperclasses().flatMap(MClass::getRawFields);
        return Arrays.stream(this.clazz.getDeclaredFields());
    }

    @Override
    public @NotNull Stream<MMethod<?>> getMethods() {
        return this.getMethods(IncludeSuperclasses.No);
    }

    @Override
    public @NotNull Stream<MMethod<?>> getMethods(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include()) return this.getSuperclasses(IncludeSelf.Yes).flatMap(MClass::getMethods);
        return this.getRawMethods().map(BasicMirrorMethod::from);
    }

    @Override
    public <F> @NotNull Stream<MMethod<F>> getMethods(@NotNull Predicate<MMethod<F>> filter) {
        return this.getMethods(filter, IncludeSuperclasses.No);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <F> Stream<MMethod<F>> getMethods(@NotNull Predicate<MMethod<F>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include())
            return this.getSuperclasses(IncludeSelf.Yes).flatMap(clazz -> clazz.getMethods(filter));
        return this.getMethods().map(method -> (MMethod<F>) method).filter(filter);
    }

    @Override
    public <F> @NotNull Optional<MMethod<F>> getMethod(@NotNull Predicate<MMethod<F>> filter) {
        return this.getMethod(filter, IncludeSuperclasses.No);
    }

    @Override
    public @NotNull <F> Optional<MMethod<F>> getMethod(@NotNull Predicate<MMethod<F>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include()) return this.getMethods(filter, includeSuperclasses).findFirst();
        return this.getMethods(filter).findFirst();
    }

    @Override
    public boolean hasMethods() {
        return this.hasMethods(IncludeSuperclasses.No);
    }

    @Override
    public boolean hasMethods(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include()) return this.getSuperclasses(IncludeSelf.Yes).anyMatch(MClass::hasMethods);
        return this.getMethodCount() > 0;
    }

    @Override
    public int getMethodCount() {
        return this.getMethodCount(IncludeSuperclasses.No);
    }

    @Override
    public int getMethodCount(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include())
            return this.getSuperclasses(IncludeSelf.Yes).mapToInt(MClass::getMethodCount).sum();
        return this.clazz.getDeclaredMethods().length;
    }

    @Override
    public @NotNull Stream<Method> getRawMethods() {
        return this.getRawMethods(IncludeSuperclasses.No);
    }

    @Override
    public @NotNull Stream<Method> getRawMethods(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.include()) return this.getSuperclasses().flatMap(MClass::getRawMethods);
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
    public @NotNull String getName() {
        return this.clazz.getName();
    }

    /**
     * Creates a new {@link BasicMirrorClass} instance. This method should not be used directly. Use
     * {@link Mirror#mirror(Class)} instead.
     *
     * @param clazz The class.
     * @param <T>   The type of the class.
     * @return The new {@link BasicMirrorClass} instance.
     */
    @ApiStatus.Internal
    @Contract(value = "_ -> new", pure = true)
    public static <T> @NotNull BasicMirrorClass<T> from(@NotNull Class<T> clazz) {
        return new BasicMirrorClass<>(clazz);
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
                .filter(annotation -> annotation.annotationType().equals(type))
                .map(type::cast)
                .findFirst();
    }

    @Override
    public boolean isAnnotated() {
        return this.getAnnotationCount() > 0;
    }

    @Override
    public int getAnnotationCount() {
        return this.clazz.getAnnotations().length;
    }
}
