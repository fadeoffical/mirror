package fade.mirror.filter;

import fade.mirror.MField;
import fade.mirror.filter.by.Annotations;
import fade.mirror.filter.by.Name;
import fade.mirror.filter.by.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a filter for fields. The filter can be used to filter fields by type, name and annotations.
 * <p>
 * A new filter can be created using {@link Filter#forFields()} or constructed using the {@link FieldFilter#copy()}
 * field on an existing filter.
 * </p>
 *
 * @param <T> the type of the field
 * @author fade
 */
public interface FieldFilter<T>
        extends Filter<MField<T>>, Annotations<FieldFilter<T>>, Name<FieldFilter<T>>, Type<FieldFilter<T>> {

    <C> @NotNull FieldFilter<C> ofType(@NotNull Class<C> type);
}
