package fade.mirror.filter;

import fade.mirror.internal.impl.filter.BasicConstructorFilter;
import fade.mirror.internal.impl.filter.BasicFieldFilter;
import fade.mirror.internal.impl.filter.BasicMethodFilter;
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
     * Returns a new method filter. This type of filter can be used to filter methods by name, parameters, return type and
     * annotations.
     *
     * @see MethodFilter
     * @return the method filter
     */
    public static @NotNull MethodFilter forMethods() {
        return BasicMethodFilter.create();
    }

    /**
     * Returns a new constructor filter. This type of filter can be used to filter constructors by parameters and annotations.
     *
     * @see ConstructorFilter
     * @return the constructor filter
     */
    public static @NotNull ConstructorFilter forConstructors() {
        return BasicConstructorFilter.create();
    }

    /**
     * Returns a new field filter. This type of filter can be used to filter fields by type, name and annotations.
     *
     * @see FieldFilter
     * @return the field filter
     */
    public static @NotNull FieldFilter forFields() {
        return BasicFieldFilter.create();
    }

    // TODO: Add filters for parameters
}
