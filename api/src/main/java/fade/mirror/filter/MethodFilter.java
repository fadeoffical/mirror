package fade.mirror.filter;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

/**
 * Represents a filter for methods. The filter can be used to filter methods by name, parameters and annotations.
 *
 * @author fade
 */
public interface MethodFilter {

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
    @NotNull MethodFilter withName(@NotNull String name);

    /**
     * Clears the name of this filter. The filter will not filter by name anymore.
     *
     * @return this filter
     */
    @NotNull MethodFilter clearName();

    /**
     * Adds required parameters to this filter. The method filter will only keep methods with the specified parameters.
     * <p>
     * If the parameters are already set, the new parameters will replace the old ones.
     * <br>
     * If the parameters are empty, the filter will not keep any methods.
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
    @NotNull MethodFilter withParameters(@NotNull Class<?>... parameterTypes);

    /**
     * Clears the parameters of this filter. The filter will not filter by parameters anymore.
     *
     * @return this filter
     */
    @NotNull MethodFilter clearParameters();

    /**
     * Adds required annotations to this filter. The method filter will only keep methods with the specified annotations.
     * <p>
     *     If the annotations are already set, the new annotations will replace the old ones.
     *     <br>
     *     If the annotations are empty, the filter ignores the annotations.
     *     <br>
     *     If the annotations are not empty, the filter will only keep methods with the specified annotations.
     *     <br>
     *     The filter will only keep methods with the specified annotations but ignores all other annotations on the method.
     * </p>
     *
     * @param annotations the annotations of the method
     * @return this filter
     */
    @NotNull MethodFilter withAnnotations(@NotNull Annotation... annotations);

    /**
     * Clears the annotations of this filter. The filter will not filter by annotations anymore.
     *
     * @return this filter
     */
    @NotNull MethodFilter clearAnnotations();

}
