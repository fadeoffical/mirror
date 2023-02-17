package fade.mirror;

import fade.mirror.internal.impl.MClassImpl;
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
 *
 * @author fade <truefadeoffical@gmail.com>
 */
public sealed interface MClass<T> extends Annotated, Named
        permits MClassImpl {

    /**
     * Returns the raw class represented by this object.
     *
     * @return The raw class.
     */
    @NotNull Class<T> getRawClass();

    @NotNull Stream<MConstructor<T>> getConstructors();

    @NotNull Stream<MConstructor<T>> getConstructors(@NotNull Predicate<MConstructor<T>> filter);

    @NotNull Optional<MConstructor<T>> getConstructor();

    @NotNull Optional<MConstructor<T>> getConstructor(@NotNull Predicate<MConstructor<T>> filter);

    @NotNull Optional<MConstructor<T>> getConstructorWithTypes(@NotNull Class<?>... types);

    int getConstructorCount();

    @NotNull Stream<Constructor<T>> getRawConstructors();

    @NotNull Stream<MField<?>> getFields();

    @NotNull Stream<MField<?>> getFields(@NotNull Predicate<MField<?>> filter);

    @NotNull Optional<MField<?>> getField(@NotNull Predicate<MField<?>> filter);

    boolean hasFields();

    int getFieldCount();

    @NotNull Stream<Field> getRawFields();

    @NotNull Stream<MMethod<?>> getMethods();

    @NotNull Stream<MMethod<?>> getMethods(@NotNull Predicate<MMethod<?>> filter);

    @NotNull Optional<MMethod<?>> getMethod(@NotNull Predicate<MMethod<?>> filter);

    boolean hasMethods();

    int getMethodCount();

    @NotNull Stream<Method> getRawMethods();

    @NotNull String getSimpleName();

    @NotNull String getCanonicalName();

}
