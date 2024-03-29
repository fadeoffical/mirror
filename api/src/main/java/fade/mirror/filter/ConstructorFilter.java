package fade.mirror.filter;

import fade.mirror.MConstructor;
import fade.mirror.filter.by.ByAnnotations;
import fade.mirror.filter.by.Parameters;

/**
 * Represents a filter for constructors. The filter can be used to filter constructors by parameters and annotations.
 * <p>
 * A new filter can be created using {@link Filter#forConstructors()} or constructed using the
 * {@link ConstructorFilter#copy()} method on an existing filter.
 * </p>
 *
 * @author fade
 */
public interface ConstructorFilter
        extends Filter<MConstructor<?>>, Parameters<ConstructorFilter>, ByAnnotations<ConstructorFilter> {
}
