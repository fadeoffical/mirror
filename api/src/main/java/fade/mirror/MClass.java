package fade.mirror;

import fade.mirror.internal.impl.BasicMirrorClass;
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
 * @param <T> The type of the class.
 * @author fade
 */
public sealed interface MClass<T>
        extends Annotated, Named
        permits BasicMirrorClass {

    /**
     * Returns the raw class represented by this object.
     *
     * @return The raw class.
     */
    @NotNull Class<T> getRawClass();

    /**
     * Returns a stream of all constructors of this class. The stream is ordered by the declaration order of the
     * constructors in the source code. The stream may be empty if the class has no constructors. The stream will never
     * be {@code null}.
     *
     * @return a constructor stream.
     */
    @NotNull Stream<MConstructor<T>> getConstructors();

    /**
     * Returns a stream of all constructors of this class that match the given filter. The stream is ordered by the
     * declaration order of the constructors in the source code. The stream may be empty if the class has no
     * constructors that match the filter. The stream will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return a constructor stream.
     */
    @NotNull Stream<MConstructor<T>> getConstructors(@NotNull Predicate<MConstructor<T>> filter);

    /**
     * Returns an optional containing the first constructor of this class that matches the given filter. The optional
     * may be empty if the class has no constructors that match the filter. The optional will never be {@code null}.
     *
     * @return the first constructor that matches the filter.
     */
    @NotNull Optional<MConstructor<T>> getConstructor();

    /**
     * Returns an optional containing the first constructor of this class that matches the given filter. The optional
     * may be empty if the class has no constructors that match the filter. The optional will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return the first constructor that matches the filter.
     */
    @NotNull Optional<MConstructor<T>> getConstructor(@NotNull Predicate<MConstructor<T>> filter);

    /**
     * Returns an optional containing the constructor of this class that has the given parameter types. The optional may
     * be empty if the class has no constructors that match the filter. The optional will never be {@code null}.
     *
     * @param types the parameter types.
     * @return the constructor with the given parameter types.
     */
    @NotNull Optional<MConstructor<T>> getConstructorWithTypes(@NotNull Class<?>... types);

    /**
     * Returns the number of constructors of this class.
     *
     * @return the number of constructors.
     */
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
    @NotNull Stream<Constructor<T>> getRawConstructors();

    /**
     * Returns a stream of all fields of this class. The stream is ordered by the declaration order of the fields in the
     * source code. The stream may be empty if the class has no fields. The stream will never be {@code null}.
     *
     * @return a field stream.
     */
    @NotNull Stream<MField<?>> getFields();

    /**
     * Returns a stream of all fields of this class that match the given filter. The stream is ordered by the
     * declaration order of the fields in the source code. The stream may be empty if the class has no fields that match
     * the filter. The stream will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return a field stream.
     */
    @NotNull Stream<MField<?>> getFields(@NotNull Predicate<MField<?>> filter);

    /**
     * Returns an optional containing the first field of this class that matches the given filter. The optional may be
     * empty if the class has no fields that match the filter. The optional will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return the first field that matches the filter.
     */
    <F> @NotNull Optional<MField<F>> getField(@NotNull Predicate<MField<F>> filter);

    /**
     * Returns whether this class has any fields.
     *
     * @return {@code true} if this class has any fields, {@code false} otherwise.
     */
    boolean hasFields();

    /**
     * Returns the number of fields of this class.
     *
     * @return the number of fields.
     */
    int getFieldCount();

    /**
     * Returns a stream of all raw fields of this class. The stream is ordered by the declaration order of the fields in
     * the source code. The stream may be empty if the class has no fields. The stream will never be {@code null}.
     * <p>
     * Unlike {@link #getFields()}, this method returns the raw fields.
     * </p>
     *
     * @return a raw field stream.
     */
    @NotNull Stream<Field> getRawFields();

    /**
     * Returns a stream of all methods of this class. The stream is ordered by the declaration order of the methods in
     * the source code. The stream may be empty if the class has no methods. The stream will never be {@code null}.
     *
     * @return a method stream.
     */
    @NotNull Stream<MMethod<?>> getMethods();

    /**
     * Returns a stream of all methods of this class that match the given filter. The stream is ordered by the
     * declaration order of the methods in the source code. The stream may be empty if the class has no methods that
     * match the filter. The stream will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return a method stream.
     */
    @NotNull Stream<MMethod<?>> getMethods(@NotNull Predicate<MMethod<?>> filter);

    /**
     * Returns an optional containing the first method of this class that matches the given filter. The optional may be
     * empty if the class has no methods that match the filter. The optional will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return the first method that matches the filter.
     */
    @NotNull Optional<MMethod<?>> getMethod(@NotNull Predicate<MMethod<?>> filter);

    /**
     * Returns whether this class has any methods.
     *
     * @return {@code true} if this class has any methods, {@code false} otherwise.
     */
    boolean hasMethods();

    /**
     * Returns the number of methods of this class.
     *
     * @return the number of methods.
     */
    int getMethodCount();

    /**
     * Returns a stream of all raw methods of this class. The stream is ordered by the declaration order of the methods
     * in the source code. The stream may be empty if the class has no methods. The stream will never be {@code null}.
     * <p>
     * Unlike {@link #getMethods()}, this method returns the raw methods.
     * </p>
     *
     * @return a raw method stream.
     */
    @NotNull Stream<Method> getRawMethods();

    /**
     * Returns the simple name of this class. The simple name is the name of the class without the package name.
     * <p>
     * For example, the simple name of the class {@code java.lang.String} is {@code String}.
     * </p>
     *
     * @return the simple name of this class.
     */
    @NotNull String getSimpleName();

    /**
     * Returns the canonical name of this class. The canonical name is the name of the class with the package name.
     * <p>
     * For example, the canonical name of the class {@code java.lang.String} is {@code java.lang.String}.
     * </p>
     *
     * @return the canonical name of this class.
     */
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
    @Override
    @NotNull String getName();
}
