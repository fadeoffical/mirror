package fade.mirror.filter;

import fade.mirror.MMethod;
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
        extends Filter<MMethod<T>>{

    <C> @NotNull MethodFilter<C> ofType(@NotNull Class<C> type);

    @Override
    @NotNull MethodFilter<T> copy();
}
