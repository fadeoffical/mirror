package fade.mirror.filter;

import fade.mirror.MParameter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a filter for parameters. The filter can be used to filter parameters by type, name and annotations.
 * <p>
 * A new filter can be created using {@link Filter#forParameters()} or constructed using the
 * {@link ParameterSubjectFilter#copy()} parameter on an existing filter.
 * </p>
 *
 * @author fade
 */
public interface ParameterSubjectFilter // todo: should it be generic? see by.Type
        extends Filter<MParameter<?>>, AnnotationCriterion<ParameterSubjectFilter>,
                NameFilter<ParameterSubjectFilter> {

    <C> @NotNull ParameterSubjectFilter ofType(Class<C> type);
}
