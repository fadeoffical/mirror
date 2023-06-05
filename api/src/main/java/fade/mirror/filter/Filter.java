package fade.mirror.filter;

import fade.mirror.Copyable;
import fade.mirror.internal.impl.filter.ConstructorFilterImpl;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * Represents a filter. A filter can be used to filter elements of a class, method, constructor or parameter.
 *
 * @param <SubjectType> The type of the element to filter.
 * @author fade
 */
public interface Filter<SubjectType>
        extends Predicate<SubjectType>,
                Copyable<Filter<SubjectType>> {

    /**
     * Returns a new constructor filter. This type of filter can be used to filter constructors by parameters and
     * annotations.
     *
     * @return the constructor filter
     * @see ConstructorFilter
     */
    static <Type> @NotNull ConstructorFilter<Type> forConstructors() {
        return ConstructorFilterImpl.create();
    }

    /**
     * Returns a new method filter. This type of filter can be used to filter methods by name, parameters, return type
     * and annotations.
     *
     * @return the method filter
     * @see MethodFilter
     */
    static <Type> @NotNull MethodFilter<Type> forMethods() {
        return MethodFilterImpl.create();
    }

    /**
     * Returns a new field filter. This type of filter can be used to filter fields by type, name and annotations.
     *
     * @return the field filter
     * @see FieldSubjectFilter
     */
    static @NotNull FieldSubjectFilter<?> forFields() {
        return BasicFieldSubjectFilter.create();
    }

    /**
     * Returns a new parameter filter. This type of filter can be used to filter parameters by type, name and
     * annotations.
     *
     * @return the parameter filter
     * @see ParameterSubjectFilter
     */
    static @NotNull ParameterSubjectFilter forParameters() {
        return BasicParameterSubjectFilter.create();
    }

}
