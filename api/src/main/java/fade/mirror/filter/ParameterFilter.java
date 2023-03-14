package fade.mirror.filter;

import fade.mirror.MParameter;
import fade.mirror.filter.by.Annotations;
import fade.mirror.filter.by.Name;
import fade.mirror.filter.by.Type;
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
public interface ParameterFilter // todo: should it be generic? see by.Type
        extends Filter<MParameter<?>>, Annotations<ParameterFilter>, Type<ParameterFilter>, Name<ParameterFilter> {

    <C> @NotNull ParameterFilter ofType(Class<C> type);
}
