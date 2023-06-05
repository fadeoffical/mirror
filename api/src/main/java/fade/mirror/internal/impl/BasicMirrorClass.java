package fade.mirror.internal.impl;

import fade.mirror.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Basic implementation of {@link MClass}.
 *
 * @param <Type> The type of the class.
 */
public final class BasicMirrorClass<Type>
        implements MClass<Type> {

    /**
     * The class.
     */
    private final @NotNull Class<Type> clazz;

    /**
     * Creates a new {@link BasicMirrorClass} instance.
     *
     * @param clazz The class.
     */
    @ApiStatus.Internal
    private BasicMirrorClass(@NotNull Class<Type> clazz) {
        this.clazz = clazz;
    }

    @Override
    public @NotNull Class<Type> getRawClass() {
        return this.clazz;
    }

    @Override
    public @NotNull Stream<MClass<?>> getSuperclasses() {
        return this.getSuperclasses(IncludeSelf.No);
    }

    @Override
    public @NotNull Stream<MClass<?>> getSuperclasses(@NotNull IncludeSelf includeSelf) {
        List<MClass<?>> classes = new ArrayList<>(1);
        Optional<MClass<?>> optionalClass = includeSelf.asBoolean() ? Optional.of(this) : this.getSuperclass();

        do {
            if (optionalClass.isEmpty()) break;
            classes.add(optionalClass.get());
            optionalClass = optionalClass.get().getSuperclass();
        } while (optionalClass.isPresent() && optionalClass.get().hasSuperclass());

        return classes.stream();
    }

    @Override
    public @NotNull Optional<MClass<?>> getSuperclass() {
        Class<? super Type> superclass = this.clazz.getSuperclass();
        return Optional.ofNullable(this.hasSuperclass() ? from(superclass) : null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <ClassType> Optional<MClass<ClassType>> getSuperclassUntil(@NotNull Predicate<MClass<ClassType>> filter, @NotNull MClass.IncludeSelf includeSelf) {
        Optional<MClass<?>> optionalClass = includeSelf.asBoolean() ? Optional.of(this) : this.getSuperclass();

        do {
            if (optionalClass.isEmpty()) return Optional.empty();
            MClass<ClassType> clazz = (MClass<ClassType>) optionalClass.get();
            if (filter.test(clazz)) return Optional.of(clazz);
            optionalClass = clazz.getSuperclass();
        } while (optionalClass.isPresent() && optionalClass.get().hasSuperclass());
        return Optional.empty();
    }

    /**
     * Creates a new {@link BasicMirrorClass} instance. This method should not be used directly. Use
     * {@link Mirror#mirror(Class)} instead.
     *
     * @param clazz  The class.
     * @param <Type> The type of the class.
     * @return The new {@link BasicMirrorClass} instance.
     */
    @ApiStatus.Internal
    @Contract(value = "_ -> new", pure = true)
    public static <Type> @NotNull BasicMirrorClass<Type> from(@NotNull Class<Type> clazz) {
        return new BasicMirrorClass<>(clazz);
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
    public <O extends Type> @NotNull Type cast(@NotNull O object) {
        return this.clazz.cast(object);
    }

    @Override
    public @NotNull Stream<MClass<?>> getInnerClasses(@NotNull MClass.RecurseInnerClasses recurseInnerClasses, @NotNull IncludeSelf includeSelf) {
        Class<?>[] subClasses = this.clazz.getDeclaredClasses();

        return new ArrayList<MClass<?>>(subClasses.length + (includeSelf.asBoolean() ? 1 : 0)) {{
            if (includeSelf.asBoolean()) this.add(BasicMirrorClass.this);

            Arrays.stream(subClasses).map(BasicMirrorClass::from).forEach(mClass -> {
                this.add(mClass);
                if (recurseInnerClasses.asBoolean()) this.addAll(mClass.getInnerClasses(recurseInnerClasses).toList());
            });
        }}.stream();
    }

    @Override
    public @NotNull Stream<MConstructor<Type>> getConstructors() {
        return this.getRawConstructors().map(BasicMirrorConstructor::from);
    }

    @Override
    public int getConstructorCount() {
        return this.clazz.getConstructors().length;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Stream<Constructor<Type>> getRawConstructors() {
        return Arrays.stream(this.clazz.getConstructors()).map(constructor -> (Constructor<Type>) constructor);
    }

    @Override
    public @NotNull Stream<MField<?>> getFields(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean()) return this.getSuperclasses(IncludeSelf.Yes).flatMap(MClass::getFields);

        return this.getRawFields().map(BasicMirrorField::from);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <FieldType> Stream<MField<FieldType>> getFields(@NotNull Predicate<MField<FieldType>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean())
            return this.getSuperclasses(IncludeSelf.Yes).flatMap(clazz -> clazz.getFields(filter));
        return this.getFields().map(field -> (MField<FieldType>) field).filter(filter);
    }

    @Override
    public @NotNull <FieldType> Optional<MField<FieldType>> getField(@NotNull Predicate<MField<FieldType>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean()) return this.getFields(filter, includeSuperclasses).findFirst();
        return this.getFields(filter).findFirst();
    }

    @Override
    public boolean hasFields(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean()) return this.getSuperclasses(IncludeSelf.Yes).anyMatch(MClass::hasFields);
        return this.getFieldCount() > 0;
    }

    @Override
    public int getFieldCount(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean())
            return this.getSuperclasses(IncludeSelf.Yes).mapToInt(MClass::getFieldCount).sum();
        return this.clazz.getDeclaredFields().length;
    }

    @Override
    public @NotNull Stream<Field> getRawFields() {
        return this.getRawFields(IncludeSuperclasses.No);
    }

    @Override
    public @NotNull Stream<Field> getRawFields(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean()) return this.getSuperclasses().flatMap(MClass::getRawFields);
        return Arrays.stream(this.clazz.getDeclaredFields());
    }

    @Override
    public @NotNull Stream<MMethod<?>> getMethods() {
        return this.getMethods(IncludeSuperclasses.No);
    }

    @Override
    public @NotNull Stream<MMethod<?>> getMethods(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean()) return this.getSuperclasses(IncludeSelf.Yes).flatMap(MClass::getMethods);
        return this.getRawMethods().map(BasicMirrorMethod::from);
    }

    @Override
    public <MethodType> @NotNull Stream<MMethod<MethodType>> getMethods(@NotNull Predicate<MMethod<MethodType>> filter) {
        return this.getMethods(filter, IncludeSuperclasses.No);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <MethodType> Stream<MMethod<MethodType>> getMethods(@NotNull Predicate<MMethod<MethodType>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean())
            return this.getSuperclasses(IncludeSelf.Yes).flatMap(clazz -> clazz.getMethods(filter));
        return this.getMethods().map(method -> (MMethod<MethodType>) method).filter(filter);
    }

    @Override
    public <MethodType> @NotNull Optional<MMethod<MethodType>> getMethod(@NotNull Predicate<MMethod<MethodType>> filter) {
        return this.getMethod(filter, IncludeSuperclasses.No);
    }

    @Override
    public @NotNull <MethodType> Optional<MMethod<MethodType>> getMethod(@NotNull Predicate<MMethod<MethodType>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean()) return this.getMethods(filter, includeSuperclasses).findFirst();
        return this.getMethods(filter).findFirst();
    }

    @Override
    public boolean hasMethods() {
        return this.hasMethods(IncludeSuperclasses.No);
    }

    @Override
    public boolean hasMethods(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean()) return this.getSuperclasses(IncludeSelf.Yes).anyMatch(MClass::hasMethods);
        return this.getMethodCount() > 0;
    }

    @Override
    public int getMethodCount() {
        return this.getMethodCount(IncludeSuperclasses.No);
    }

    @Override
    public int getMethodCount(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean())
            return this.getSuperclasses(IncludeSelf.Yes).mapToInt(MClass::getMethodCount).sum();
        return this.clazz.getDeclaredMethods().length;
    }

    @Override
    public @NotNull Stream<Method> getRawMethods() {
        return this.getRawMethods(IncludeSuperclasses.No);
    }

    @Override
    public @NotNull Stream<Method> getRawMethods(@NotNull MClass.IncludeSuperclasses includeSuperclasses) {
        if (includeSuperclasses.asBoolean()) return this.getSuperclasses().flatMap(MClass::getRawMethods);
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

    @Override
    public @NotNull Stream<Annotation> getAnnotations() {
        return Arrays.stream(this.clazz.getAnnotations());
    }

    @Override
    public int getAnnotationCount() {
        return this.clazz.getAnnotations().length;
    }
}
