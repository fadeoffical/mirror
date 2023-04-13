package fade.mirror.filter;

import fade.mirror.MMethod;
import fade.mirror.filter.by.ByAnnotations;
import fade.mirror.filter.by.ByName;
import fade.mirror.filter.by.ByType;
import fade.mirror.filter.by.Parameters;
import org.jetbrains.annotations.NotNull;

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
public interface MethodFilter<Type>
        extends Filter<MMethod<Type>>,
                ByAnnotations<MethodFilter<Type>>,
                Parameters<MethodFilter<Type>>,
                ByType<MethodFilter<Type>>,
                ByName<MethodFilter<Type>> {

    <ClassType> @NotNull MethodFilter<ClassType> ofType(@NotNull Class<ClassType> type);
}
