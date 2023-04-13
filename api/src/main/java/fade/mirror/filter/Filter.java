package fade.mirror.filter;

import fade.mirror.Copyable;
import fade.mirror.internal.impl.filter.BasicConstructorFilter;
import fade.mirror.internal.impl.filter.BasicFieldFilter;
import fade.mirror.internal.impl.filter.BasicMethodFilter;
import fade.mirror.internal.impl.filter.BasicParameterFilter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Represents a filter. A filter can be used to filter elements of a class, method, constructor or parameter.
 *
 * @param <Type> The type of the element to filter.
 * @author fade
 */
public interface Filter<Type>
        extends Predicate<Type>, Copyable<Filter<Type>> {

    /**
     * Returns a new method filter. This type of filter can be used to filter methods by name, parameters, return type
     * and annotations.
     *
     * @return the method filter
     * @see MethodFilter
     */
    static @NotNull MethodFilter<?> forMethods() {
        return BasicMethodFilter.create();
    }

    /**
     * Returns a new constructor filter. This type of filter can be used to filter constructors by parameters and
     * annotations.
     *
     * @return the constructor filter
     * @see ConstructorFilter
     */
    static @NotNull ConstructorFilter forConstructors() {
        return BasicConstructorFilter.create();
    }

    /**
     * Returns a new field filter. This type of filter can be used to filter fields by type, name and annotations.
     *
     * @return the field filter
     * @see FieldFilter
     */
    static @NotNull FieldFilter<?> forFields() {
        return BasicFieldFilter.create();
    }

    /**
     * Returns a new parameter filter. This type of filter can be used to filter parameters by type, name and
     * annotations.
     *
     * @return the parameter filter
     * @see ParameterFilter
     */
    static @NotNull ParameterFilter forParameters() {
        return BasicParameterFilter.create();
    }

}
