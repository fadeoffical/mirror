package fade.mirror.filter;

import fade.mirror.MMethod;
import fade.mirror.filter.by.Annotations;
import fade.mirror.filter.by.Name;
import fade.mirror.filter.by.Parameters;
import fade.mirror.filter.by.Type;
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
public interface MethodFilter<T>
        extends Filter<MMethod<T>>,
                Annotations<MethodFilter<T>>,
                Parameters<MethodFilter<T>>,
                Type<MethodFilter<T>>,
                Name<MethodFilter<T>> {

    <C> @NotNull MethodFilter<C> ofType(@NotNull Class<C> type);
}
