package fade.mirror.filter;

import fade.mirror.MField;
import fade.mirror.filter.criterion.AnnotationCriterion;
import fade.mirror.filter.criterion.NameFilter;
import fade.mirror.filter.criterion.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a filter for fields. The filter can be used to filter fields by type, name and annotations.
 * <p>
 * A new filter can be created using {@link Filter#forFields()} or constructed using the {@link FieldSubjectFilter#copy()}
 * field on an existing filter.
 * </p>
 *
 * @param <T> the type of the field
 * @author fade
 */
public interface FieldSubjectFilter<T>
        extends Filter<MField<T>>,
                AnnotationCriterion<FieldSubjectFilter<T>>, NameFilter<FieldSubjectFilter<T>>, Type<FieldSubjectFilter<T>> {

    <C> @NotNull FieldSubjectFilter<C> ofType(@NotNull Class<C> type);
}
