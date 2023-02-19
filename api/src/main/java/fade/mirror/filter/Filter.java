package fade.mirror.filter;

import fade.mirror.internal.impl.filter.BasicConstructorFilter;
import fade.mirror.internal.impl.filter.BasicFieldFilter;
import fade.mirror.internal.impl.filter.BasicMethodFilter;
import fade.mirror.internal.impl.filter.BasicParameterFilter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * A filter is a predicate that can be used to filter mirror objects.
 *
 * @param <T> the type of the mirror object to be filtered
 * @author fade
 */
public abstract class Filter<T>
        implements Predicate<T> {

    /**
     * Returns a new method filter. This type of filter can be used to filter methods by name, parameters, return type
     * and annotations.
     *
     * @return the method filter
     * @see MethodFilter
     */
    public static @NotNull MethodFilter forMethods() {
        return BasicMethodFilter.create();
    }

    /**
     * Returns a new constructor filter. This type of filter can be used to filter constructors by parameters and
     * annotations.
     *
     * @return the constructor filter
     * @see ConstructorFilter
     */
    public static @NotNull ConstructorFilter forConstructors() {
        return BasicConstructorFilter.create();
    }

    /**
     * Returns a new field filter. This type of filter can be used to filter fields by type, name and annotations.
     *
     * @return the field filter
     * @see FieldFilter
     */
    public static @NotNull FieldFilter forFields() {
        return BasicFieldFilter.create();
    }

    /**
     * Returns a new parameter filter. This type of filter can be used to filter parameters by type, name and
     * annotations.
     *
     * @return the parameter filter
     * @see ParameterFilter
     */
    public static @NotNull ParameterFilter forParameters() {
        return BasicParameterFilter.create();
    }

    // TODO: Add filters for parameters
}
