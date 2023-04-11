package fade.mirror.filter;

import fade.mirror.MField;
import fade.mirror.filter.by.ByAnnotations;
import fade.mirror.filter.by.ByName;
import fade.mirror.filter.by.ByType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a filter for fields. The filter can be used to filter fields by type, name and annotations.
 * <p>
 * A new filter can be created using {@link Filter#forFields()} or constructed using the {@link FieldFilter#copy()}
 * field on an existing filter.
 * </p>
 *
 * @param <Type> the type of the field
 * @author fade
 */
public interface FieldFilter<Type>
        extends Filter<MField<Type>>,
                ByAnnotations<FieldFilter<Type>>,
                ByName<FieldFilter<Type>>,
                ByType<FieldFilter<Type>> {

    <ClassType> @NotNull FieldFilter<ClassType> ofType(@NotNull Class<ClassType> type);
}
