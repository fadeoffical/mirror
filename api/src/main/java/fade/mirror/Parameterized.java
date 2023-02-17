package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents a type that can have parameters. This includes methods and constructors.
 * <p>
 * This interface is used to provide a common interface for {@link MMethod} and {@link MConstructor}. It is not intended
 * to be implemented by any other class.
 * </p>
 *
 * @author fade <truefadeoffical@gmail.com>
 */
public sealed interface Parameterized permits MConstructor, MMethod {

    /**
     * Returns a stream of all parameters of this method or constructor. The stream is ordered by the declaration order
     * of the parameters in the source code. The stream may be empty if the method or constructor has no parameters. The
     * stream will never be {@code null}.
     *
     * @return a parameter stream.
     */
    @NotNull Stream<MParameter<?>> getParameters();

    /**
     * Returns a stream of all parameters of this method or constructor that match the given filter. The stream is
     * ordered by the declaration order of the parameters in the source code. The stream may be empty if the method or
     * constructor has no parameters that match the filter. The stream will never be {@code null}.
     *
     * @param filter the filter to apply to the parameters.
     * @return a parameter stream.
     */
    @NotNull Stream<MParameter<?>> getParameters(@NotNull Predicate<MParameter<?>> filter);

    /**
     * Returns an optional containing the first parameter of this method or constructor that matches the given filter.
     * The optional may be empty if the method or constructor has no parameters that match the filter. The optional will
     * never be {@code null}.
     *
     * @param filter the filter to apply to the parameters.
     * @return the first parameter that matches the filter.
     */
    @NotNull Optional<MParameter<?>> getParameter(@NotNull Predicate<MParameter<?>> filter);

    /**
     * Returns a stream of all parameters of this method or constructor that are of the given type. The stream is
     * ordered by the declaration order of the parameters in the source code. The stream may be empty if the method or
     * constructor has no parameters of the given type. The stream will never be {@code null}.
     *
     * @param type the type of the parameters.
     * @param <T>  the type of the parameters.
     * @return a parameter stream.
     */
    <T> @NotNull Stream<MParameter<T>> getParametersOfType(@NotNull Class<T> type);

    /**
     * Returns an optional containing the first parameter of this method or constructor that is of the given type. The
     * optional may be empty if the method or constructor has no parameters of the given type. The optional will never
     * be {@code null}.
     *
     * @param type the type of the parameter.
     * @param <T>  the type of the parameter.
     * @return the first parameter of the given type.
     */
    <T> @NotNull Optional<MParameter<T>> getParameterOfType(@NotNull Class<T> type);

    /**
     * Returns a stream of all parameters of this method or constructor that have the given annotations. The stream is
     * ordered by the declaration order of the parameters in the source code. The stream may be empty if the method or
     * constructor has no parameters with the given annotations. The stream will never be {@code null}.
     *
     * @param annotations the annotations of the parameters.
     * @return a parameter stream.
     */
    @NotNull Stream<MParameter<?>> getParametersWithAnnotations(@NotNull Class<? extends Annotation>[] annotations);

    /**
     * Returns an optional containing the first parameter of this method or constructor that has the given annotations.
     * The optional may be empty if the method or constructor has no parameters with the given annotations. The optional
     * will never be {@code null}.
     *
     * @param annotations the annotations of the parameter.
     * @return the first parameter with the given annotations.
     */
    @NotNull Optional<MParameter<?>> getParameterWithAnnotations(@NotNull Class<? extends Annotation>[] annotations);

    /**
     * Returns whether this method or constructor has parameters.
     *
     * @return {@code true} if this method or constructor has parameters, {@code false} otherwise.
     */
    boolean hasParameters();

    /**
     * Returns the number of parameters of this method or constructor.
     *
     * @return the number of parameters.
     */
    int getParameterCount();
}
