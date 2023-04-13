package fade.mirror.filter;

import fade.mirror.MParameter;
import fade.mirror.filter.by.ByAnnotations;
import fade.mirror.filter.by.ByName;
import fade.mirror.filter.by.ByType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a filter for parameters. The filter can be used to filter parameters by type, name and annotations.
 * <p>
 * A new filter can be created using {@link Filter#forParameters()} or constructed using the
 * {@link ParameterFilter#copy()} parameter on an existing filter.
 * </p>
 *
 * @author fade
 */
public interface ParameterFilter // todo: should it be generic? see by.ByType
        extends Filter<MParameter<?>>,
                ByAnnotations<ParameterFilter>,
                ByType<ParameterFilter>,
                ByName<ParameterFilter> {

    <ClassType> @NotNull ParameterFilter ofType(Class<ClassType> type);
}
