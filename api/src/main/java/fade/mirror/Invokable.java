package fade.mirror;

import fade.mirror.filter.Filter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents a method or constructor that can be invoked.
 *
 * @param <T> the return type of the method or constructor
 * @author fade
 */
public interface Invokable<T> {

    /**
     * Invokes the method or constructor represented by this object.
     *
     * @param arguments the arguments to the method or constructor.
     * @return the result of the method or constructor.
     */
    default @Nullable T invokeWithNoInstance(@Nullable Object... arguments) {
        return this.invokeWithInstance(null, arguments);
    }

    @Nullable T invokeWithInstance(@Nullable Object instance, @Nullable Object... arguments);

    /**
     * Tests whether the method or constructor represented by this object can be invoked with the given arguments.
     *
     * @param arguments the arguments to test.
     * @return whether the method or constructor can be invoked with the given arguments.
     */
    @Contract(pure = true)
    default boolean isInvokableWith(@Nullable Object... arguments) {
        Class<?>[] argumentTypes = Arrays.stream(arguments)
                .map(object -> object == null ? null : object.getClass())
                .toArray(Class<?>[]::new);
        Class<?>[] parameterTypes = this.getParameters().map(MParameter::getType).toArray(Class<?>[]::new);

        // copied and adapted from Arrays#equals
        if (parameterTypes.length != argumentTypes.length) return false;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (argumentTypes[i] != null && !parameterTypes[i].isAssignableFrom(argumentTypes[i])) return false;
        }

        return true;
    }

    /**
     * Returns a stream of all parameters of this method or constructor. The stream is ordered by the declaration order
     * of the parameters in the source code. The stream may be empty if the method or constructor has no parameters. The
     * stream will never be {@code null}.
     *
     * @return a parameter stream.
     */
    @Contract(pure = true)
    @NotNull Stream<MParameter<?>> getParameters();

    /**
     * Gets the return type of the method or constructor represented by this object.
     *
     * @return the return type.
     */
    @Contract(pure = true)
    @NotNull Class<T> getReturnType();

    /**
     * Returns an optional containing the first parameter of this method or constructor that matches the given filter.
     * The optional may be empty if the method or constructor has no parameters that match the filter. The optional will
     * never be {@code null}.
     *
     * @param filter the filter to apply to the parameters.
     * @return the first parameter that matches the filter.
     */
    @Contract(pure = true)
    @NotNull
    default Optional<MParameter<?>> getParameter(@NotNull Predicate<MParameter<?>> filter) {
        return this.getParameters(filter).findFirst();
    }

    /**
     * Returns a stream of all parameters of this method or constructor that match the given filter. The stream is
     * ordered by the declaration order of the parameters in the source code. The stream may be empty if the method or
     * constructor has no parameters that match the filter. The stream will never be {@code null}.
     *
     * @param filter the filter to apply to the parameters.
     * @return a parameter stream.
     */
    @Contract(pure = true)
    @NotNull
    default Stream<MParameter<?>> getParameters(@NotNull Predicate<MParameter<?>> filter) {
        return this.getParameters().filter(filter);
    }

    /**
     * Returns a stream of all parameters of this method or constructor that are of the given type. The stream is
     * ordered by the declaration order of the parameters in the source code. The stream may be empty if the method or
     * constructor has no parameters of the given type. The stream will never be {@code null}.
     *
     * @param type the type of the parameters.
     * @param <P>  the type of the parameters.
     * @return a parameter stream.
     * @deprecated use {@link #getParameters(Predicate)} & {@link Filter}s instead.
     */
    @Contract(pure = true)
    @Deprecated(forRemoval = true)
    <P> @NotNull Stream<MParameter<P>> getParametersOfType(@NotNull Class<P> type);

    /**
     * Returns an optional containing the first parameter of this method or constructor that is of the given type. The
     * optional may be empty if the method or constructor has no parameters of the given type. The optional will never
     * be {@code null}.
     *
     * @param type the type of the parameter.
     * @param <P>  the type of the parameter.
     * @return the first parameter of the given type.
     * @deprecated use {@link #getParameter(Predicate)} & {@link Filter}s instead.
     */
    @Contract(pure = true)
    @Deprecated(forRemoval = true)
    <P> @NotNull Optional<MParameter<P>> getParameterOfType(@NotNull Class<P> type);

    /**
     * Returns a stream of all parameters of this method or constructor that have the given annotations. The stream is
     * ordered by the declaration order of the parameters in the source code. The stream may be empty if the method or
     * constructor has no parameters with the given annotations. The stream will never be {@code null}.
     *
     * @param annotations the annotations of the parameters.
     * @return a parameter stream.
     * @deprecated use {@link #getParameters(Predicate)} & {@link Filter}s instead.
     */
    @Contract(pure = true)
    @Deprecated(forRemoval = true)
    @NotNull Stream<MParameter<?>> getParametersWithAnnotations(@NotNull Class<? extends Annotation>[] annotations);

    /**
     * Returns an optional containing the first parameter of this method or constructor that has the given annotations.
     * The optional may be empty if the method or constructor has no parameters with the given annotations. The optional
     * will never be {@code null}.
     *
     * @param annotations the annotations of the parameter.
     * @return the first parameter with the given annotations.
     * @deprecated use {@link #getParameter(Predicate)} & {@link Filter}s instead.
     */
    @Contract(pure = true)
    @Deprecated(forRemoval = true)
    @NotNull Optional<MParameter<?>> getParameterWithAnnotations(@NotNull Class<? extends Annotation>[] annotations);

    /**
     * Returns the number of parameters of this method or constructor.
     *
     * @return the number of parameters.
     */
    @Contract(pure = true)
    int getParameterCount();

    /**
     * Returns whether this method or constructor has parameters.
     *
     * @return {@code true} if this method or constructor has parameters, {@code false} otherwise.
     */
    @Contract(pure = true)
    default boolean hasParameters() {
        return this.getParameterCount() > 0;
    }
}
