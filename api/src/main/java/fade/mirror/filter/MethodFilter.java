package fade.mirror.filter;

import fade.mirror.MMethod;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

/**
 * Represents a filter for methods. The filter can be used to filter methods by name, parameters, return type and
 * annotations.
 * <p>
 * A new filter can be created using {@link Filter#forMethods()} or constructed using the {@link MethodFilter#copy()}
 * method on an existing filter.
 * </p>
 *
 * @author fade
 */
public interface MethodFilter<T>
        extends Filter<MMethod<T>> {

    /**
     * Adds a required name to this filter. The field filter will only keep fields with the specified name.
     * <p>
     * If the name is already set, the new name will replace the old one.
     * <br>
     * If the name is empty, the filter will not keep any fields.
     * <br>
     * If the name is not empty, the filter will only keep fields with the specified name.
     * <br>
     * </p>
     *
     * @param name the name of the method
     * @return this filter
     */
    @NotNull MethodFilter<T> withName(@NotNull String name);

    /**
     * Clears the name of this filter. The filter will not filter by name anymore.
     *
     * @return this filter
     */
    @NotNull MethodFilter<T> clearName();

    /**
     * Adds required parameters to this filter. The method filter will only keep methods with the specified parameters.
     * <p>
     * If the parameters are already set, the new parameters will replace the old ones.
     * <br>
     * If the parameters are not empty, the filter will only keep methods with the specified parameters.
     * <br>
     * The filter will only keep methods with the same amount of parameters.
     * <br>
     * The filter will only keep methods with the same parameter types, or types that are assignable from the specified
     * parameter types.
     * <br>
     * The order of the parameters is important; the filter will only keep methods with the same parameter order.
     * <br>
     * </p>
     *
     * @param parameterTypes the parameter types of the method
     * @return this filter
     */
    @NotNull MethodFilter<T> withParameters(@NotNull Class<?>... parameterTypes);

    /**
     * Clears the parameters of this filter. The filter will not filter by parameters anymore.
     *
     * @return this filter
     */
    @NotNull MethodFilter<T> clearParameters();

    /**
     * Adds required annotations to this filter. The method filter will only keep methods with the specified
     * annotations.
     * <p>
     * If the annotations are already set, the new annotations will replace the old ones.
     * <br>
     * If the annotations are empty, the filter ignores the annotations.
     * <br>
     * If the annotations are not empty, the filter will only keep methods with the specified annotations.
     * <br>
     * The filter will only keep methods with the specified annotations but ignores all other annotations on the
     * method.
     * </p>
     *
     * @param annotations the annotations of the method
     * @return this filter
     */
    @NotNull MethodFilter<T> withAnnotations(@NotNull Class<? extends Annotation>... annotations);

    /**
     * Clears the annotations of this filter. The filter will not filter by annotations anymore.
     *
     * @return this filter
     */
    @NotNull MethodFilter<T> clearAnnotations();

    /**
     * Adds a required return type to this filter. The method filter will only keep methods with the specified return
     * type.
     * <p>
     * If the return type is already set, the new return type will replace the old one.
     * </p>
     *
     * @param returnType the return type of the method
     * @return this filter
     */
    <C> @NotNull MethodFilter<C> withReturnType(@NotNull Class<C> returnType);

    /**
     * Clears the return type of this filter. The filter will not filter by return type anymore.
     *
     * @return this filter
     */
    @NotNull MethodFilter<T> clearReturnType();

}
