package fade.mirror.filter;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

/**
 * Represents a filter for constructors. The filter can be used to filter constructors by parameters and annotations.
 *
 * @author fade
 */
public interface ConstructorFilter {

    /**
     * Adds required parameters to this filter. The filter will only keep constructors with the specified parameters.
     * <p>
     * If the parameters are already set, the new parameters will replace the old ones.
     * <br>
     * If the parameters are not empty, the filter will only keep constructors with the specified parameters.
     * <br>
     * The filter will only keep constructors with the same amount of parameters.
     * <br>
     * The filter will only keep constructors with the same parameter types, or types that are assignable from the
     * specified parameter types.
     * <br>
     * The order of the parameters is important; the filter will only keep constructors with the same parameter order.
     * <br>
     * </p>
     *
     * @param parameterTypes the parameter types of the constructor
     * @return this filter
     */
    @NotNull ConstructorFilter withParameters(@NotNull Class<?>... parameterTypes);

    /**
     * Clears the parameters of this filter. The filter will not filter by parameters anymore.
     *
     * @return this filter
     */
    @NotNull ConstructorFilter clearParameters();

    /**
     * Adds required annotations to this filter. The constructor filter will only keep constructors with the specified
     * annotations.
     * <p>
     * If the annotations are already set, the new annotations will replace the old ones.
     * <br>
     * If the annotations are empty, the filter ignores the annotations.
     * <br>
     * If the annotations are not empty, the filter will only keep constructors with the specified annotations.
     * <br>
     * The filter will only keep constructors with the specified annotations but ignores all other annotations on the
     * constructor.
     * </p>
     *
     * @param annotations the annotations of the constructor
     * @return this filter
     */
    @NotNull ConstructorFilter withAnnotations(@NotNull Annotation... annotations);

    /**
     * Clears the annotations of this filter. The filter will not filter by annotations anymore.
     *
     * @return this filter
     */
    @NotNull ConstructorFilter clearAnnotations();

}
