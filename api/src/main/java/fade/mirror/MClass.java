package fade.mirror;

import fade.mirror.internal.impl.BasicMirrorClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents a class.
 *
 * @param <Type> The type of the class.
 * @author fade
 */
public sealed interface MClass<Type>
        extends Annotated, Named
        permits BasicMirrorClass {

    /**
     * Returns the raw class represented by this object.
     *
     * @return The raw class.
     */
    @Contract(pure = true)
    @NotNull Class<Type> getRawClass();

    @Contract(pure = true)
    @NotNull Stream<MClass<?>> getSuperclasses();

    @Contract(pure = true)
    @NotNull Stream<MClass<?>> getSuperclasses(@NotNull MClass.IncludeSelf includeSelf);

    @Contract(pure = true)
    @SuppressWarnings("unchecked")
    default @NotNull Optional<MClass<Type>> getSuperclassAsThis() {
        return this.getSuperclass().map(clazz -> (MClass<Type>) clazz);
    }

    @Contract(pure = true)
    @NotNull Optional<MClass<?>> getSuperclass();

    @Contract(pure = true)
    default <ClassType> @NotNull Optional<MClass<ClassType>> getSuperclassUntil(@NotNull Predicate<MClass<ClassType>> filter) {
        return this.getSuperclassUntil(filter, IncludeSelf.No);
    }

    @Contract(pure = true)
    <ClassType> @NotNull Optional<MClass<ClassType>> getSuperclassUntil(@NotNull Predicate<MClass<ClassType>> filter, @NotNull MClass.IncludeSelf includeSuperclasses);

    @Contract(pure = true)
    boolean hasSuperclass();

    @Contract(pure = true)
    boolean isSuperclassOf(@NotNull MClass<?> clazz);

    @Contract(pure = true)
    boolean isSuperclassOf(@NotNull Class<?> clazz);

    @Contract(pure = true)
    <SuperType extends Type> @NotNull Type cast(@NotNull SuperType object);

    default @NotNull Optional<MClass<?>> getInnerClass() { // todo: is this method of any use?
        return this.getInnerClasses().findFirst();
    }

    default @NotNull Stream<MClass<?>> getInnerClasses() {
        return this.getInnerClasses(RecurseInnerClasses.No, IncludeSelf.No);
    }

    @Contract(pure = true)
    @NotNull Stream<MClass<?>> getInnerClasses(@NotNull MClass.RecurseInnerClasses recurseInnerClasses, @NotNull MClass.IncludeSelf includeSelf);

    default @NotNull Optional<MClass<?>> getInnerClass(@NotNull Predicate<MClass<?>> filter) {
        return this.getInnerClasses().filter(filter).findFirst();
    }

    default @NotNull Optional<MClass<?>> getInnerClass(@NotNull Predicate<MClass<?>> filter, @NotNull MClass.RecurseInnerClasses recurseInnerClasses) {
        return this.getInnerClasses(recurseInnerClasses).filter(filter).findFirst();
    }

    @NotNull
    default Stream<MClass<?>> getInnerClasses(@NotNull MClass.RecurseInnerClasses recurseInnerClasses) {
        return this.getInnerClasses(recurseInnerClasses, IncludeSelf.No);
    }

    default @NotNull Optional<MClass<?>> getInnerClass(@NotNull Predicate<MClass<?>> filter, @NotNull MClass.IncludeSelf includeSelf) {
        return this.getInnerClasses(includeSelf).filter(filter).findFirst();
    }

    @NotNull
    default Stream<MClass<?>> getInnerClasses(@NotNull MClass.IncludeSelf includeSelf) {
        return this.getInnerClasses(RecurseInnerClasses.No, includeSelf);
    }

    default @NotNull Optional<MClass<?>> getInnerClass(@NotNull Predicate<MClass<?>> filter, @NotNull MClass.RecurseInnerClasses recurseInnerClasses, @NotNull MClass.IncludeSelf includeSelf) {
        return this.getInnerClasses(recurseInnerClasses, includeSelf).filter(filter).findFirst();
    }

    /**
     * Returns an optional containing the first constructor of this class that matches the given filter. The optional
     * may be empty if the class has no constructors that match the filter. The optional will never be {@code null}.
     *
     * @return the first constructor that matches the filter.
     */
    @Contract(pure = true)
    default @NotNull Optional<MConstructor<Type>> getConstructor() {
        return this.getConstructors().findFirst();
    }

    /**
     * Returns a stream of all constructors of this class. The stream is ordered by the declaration order of the
     * constructors in the source code. The stream may be empty if the class has no constructors. The stream will never
     * be {@code null}.
     *
     * @return a constructor stream.
     */
    @Contract(pure = true)
    @NotNull Stream<MConstructor<Type>> getConstructors();

    /**
     * Returns an optional containing the first constructor of this class that matches the given filter. The optional
     * may be empty if the class has no constructors that match the filter. The optional will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return the first constructor that matches the filter.
     */
    @Contract(pure = true)
    default @NotNull Optional<MConstructor<Type>> getConstructor(@NotNull Predicate<MConstructor<Type>> filter) {
        return this.getConstructors(filter).findFirst();
    }

    /**
     * Returns a stream of all constructors of this class that match the given filter. The stream is ordered by the
     * declaration order of the constructors in the source code. The stream may be empty if the class has no
     * constructors that match the filter. The stream will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return a constructor stream.
     */
    @Contract(pure = true)
    default @NotNull Stream<MConstructor<Type>> getConstructors(@NotNull Predicate<MConstructor<Type>> filter) {
        return this.getConstructors().filter(filter);
    }

    /**
     * Returns the number of constructors of this class.
     *
     * @return the number of constructors.
     */
    @Contract(pure = true)
    int getConstructorCount();

    /**
     * Returns a stream of all raw constructors of this class. The stream is ordered by the declaration order of the
     * constructors in the source code. The stream may be empty if the class has no constructors. The stream will never
     * be {@code null}.
     * <p>
     * Unlike {@link #getConstructors()}, this method returns the raw constructors.
     * </p>
     *
     * @return a raw constructor stream.
     */
    @Contract(pure = true)
    @NotNull Stream<Constructor<Type>> getRawConstructors();

    /**
     * Returns a stream of all fields of this class. The stream is ordered by the declaration order of the fields in the
     * source code. The stream may be empty if the class has no fields. The stream will never be {@code null}.
     *
     * @return a field stream.
     */
    @Contract(pure = true)
    default @NotNull Stream<MField<?>> getFields() {
        return this.getFields(MClass.IncludeSuperclasses.No);
    }

    @Contract(pure = true)
    @NotNull Stream<MField<?>> getFields(@NotNull MClass.IncludeSuperclasses includeSuperclasses);

    @Contract(pure = true)
    <FieldType> @NotNull Stream<MField<FieldType>> getFields(@NotNull Predicate<MField<FieldType>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns a stream of all fields of this class that match the given filter. The stream is ordered by the
     * declaration order of the fields in the source code. The stream may be empty if the class has no fields that match
     * the filter. The stream will never be {@code null}.
     *
     * @param filter      the filter to apply.
     * @param <FieldType> the type of the field.
     * @return a field stream.
     */
    @Contract(pure = true)
    default <FieldType> @NotNull Stream<MField<FieldType>> getFields(@NotNull Predicate<MField<FieldType>> filter) {
        return this.getFields(filter, MClass.IncludeSuperclasses.No);
    }

    @Contract(pure = true)
    <FieldType> @NotNull Optional<MField<FieldType>> getField(@NotNull Predicate<MField<FieldType>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns an optional containing the first field of this class that matches the given filter. The optional may be
     * empty if the class has no fields that match the filter. The optional will never be {@code null}.
     *
     * @param filter      the filter to apply.
     * @param <FieldType> the type of the field.
     * @return the first field that matches the filter.
     */
    @Contract(pure = true)
    default <FieldType> @NotNull Optional<MField<FieldType>> getField(@NotNull Predicate<MField<FieldType>> filter) {
        return this.getField(filter, MClass.IncludeSuperclasses.No);
    }

    /**
     * Returns whether this class has any fields.
     *
     * @return {@code true} if this class has any fields, {@code false} otherwise.
     */
    @Contract(pure = true)
    default boolean hasFields() {
        return this.hasFields(MClass.IncludeSuperclasses.No);
    }

    @Contract(pure = true)
    boolean hasFields(@NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns the number of fields of this class.
     *
     * @return the number of fields.
     */
    @Contract(pure = true)
    default int getFieldCount() {
        return this.getFieldCount(MClass.IncludeSuperclasses.No);
    }

    @Contract(pure = true)
    int getFieldCount(@NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns a stream of all raw fields of this class. The stream is ordered by the declaration order of the fields in
     * the source code. The stream may be empty if the class has no fields. The stream will never be {@code null}.
     * <p>
     * Unlike {@link #getFields()}, this method returns the raw fields.
     * </p>
     *
     * @return a raw field stream.
     */
    @Contract(pure = true)
    @NotNull Stream<Field> getRawFields();

    @Contract(pure = true)
    @NotNull Stream<Field> getRawFields(@NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns a stream of all methods of this class. The stream is ordered by the declaration order of the methods in
     * the source code. The stream may be empty if the class has no methods. The stream will never be {@code null}.
     *
     * @return a method stream.
     */
    @Contract(pure = true)
    @NotNull Stream<MMethod<?>> getMethods();

    @Contract(pure = true)
    @NotNull Stream<MMethod<?>> getMethods(@NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns a stream of all methods of this class that match the given filter. The stream is ordered by the
     * declaration order of the methods in the source code. The stream may be empty if the class has no methods that
     * match the filter. The stream will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return a method stream.
     */
    @Contract(pure = true)
    <MethodType> @NotNull Stream<MMethod<MethodType>> getMethods(@NotNull Predicate<MMethod<MethodType>> filter);

    @Contract(pure = true)
    <MethodType> @NotNull Stream<MMethod<MethodType>> getMethods(@NotNull Predicate<MMethod<MethodType>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns an optional containing the first method of this class that matches the given filter. The optional may be
     * empty if the class has no methods that match the filter. The optional will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return the first method that matches the filter.
     */
    @Contract(pure = true)
    <FieldType> @NotNull Optional<MMethod<FieldType>> getMethod(@NotNull Predicate<MMethod<FieldType>> filter);

    @Contract(pure = true)
    <FieldType> @NotNull Optional<MMethod<FieldType>> getMethod(@NotNull Predicate<MMethod<FieldType>> filter, @NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns whether this class has any methods.
     *
     * @return {@code true} if this class has any methods, {@code false} otherwise.
     */
    @Contract(pure = true)
    boolean hasMethods();

    @Contract(pure = true)
    boolean hasMethods(@NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns the number of methods of this class.
     *
     * @return the number of methods.
     */
    @Contract(pure = true)
    int getMethodCount();

    @Contract(pure = true)
    int getMethodCount(@NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns a stream of all raw methods of this class. The stream is ordered by the declaration order of the methods
     * in the source code. The stream may be empty if the class has no methods. The stream will never be {@code null}.
     * <p>
     * Unlike {@link #getMethods()}, this method returns the raw methods.
     * </p>
     *
     * @return a raw method stream.
     */
    @Contract(pure = true)
    @NotNull Stream<Method> getRawMethods();

    @Contract(pure = true)
    @NotNull Stream<Method> getRawMethods(@NotNull MClass.IncludeSuperclasses includeSuperclasses);

    /**
     * Returns the simple name of this class. The simple name is the name of the class without the package name.
     * <p>
     * For example, the simple name of the class {@code java.lang.String} is {@code String}.
     * </p>
     *
     * @return the simple name of this class.
     */
    @Contract(pure = true)
    @NotNull String getSimpleName();

    /**
     * Returns the canonical name of this class. The canonical name is the name of the class with the package name.
     * <p>
     * For example, the canonical name of the class {@code java.lang.String} is {@code java.lang.String}.
     * </p>
     *
     * @return the canonical name of this class.
     */
    @Contract(pure = true)
    @NotNull String getCanonicalName();

    /**
     * Returns the name of this class. The name is the name of the class with the package name and the generic type
     * parameters.
     * <p>
     * For example, the name of the class {@code java.lang.String} is {@code Ljava/lang/String;}.
     * </p>
     *
     * @return the name of this class.
     */
    @Contract(pure = true)
    @Override
    @NotNull String getName();

    enum IncludeSuperclasses
            implements MClass.Include {
        Yes, No;

        @Override
        @Contract(pure = true)
        public boolean asBoolean() {
            return this == Yes;
        }
    }

    enum IncludeSelf
            implements MClass.Include {
        Yes, No;

        @Override
        @Contract(pure = true)
        public boolean asBoolean() {
            return this == Yes;
        }
    }

    enum RecurseInnerClasses
            implements MClass.Include {
        Yes, No;

        @Override
        @Contract(pure = true)
        public boolean asBoolean() {
            return this == Yes;
        }
    }

    interface Include {

        @Contract(pure = true)
        boolean asBoolean();
    }
}
