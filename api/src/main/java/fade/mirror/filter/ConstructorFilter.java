package fade.mirror.filter;

import fade.mirror.MConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

/**
 * Represents a filter for constructors. The filter can be used to filter constructors by parameters and annotations.
 * <p>
 * A new filter can be created using {@link Filter#forConstructors()} or constructed using the
 * {@link ConstructorFilter#copy()} method on an existing filter.
 * </p>
 *
 * @author fade
 */
public interface ConstructorFilter<Type>
        extends Filter<MConstructor<Type>> {

    @NotNull ConstructorFilter<Type> withAnnotation(@NotNull Class<? extends Annotation> annotation, @NotNull TypeComparisonBy comparisonBy);

    @NotNull
    default ConstructorFilter<Type> withAnnotation(@NotNull Class<? extends Annotation> annotation) {
        return this.withAnnotation(annotation, TypeComparisonBy.Assignability);
    }

    @Override
    @NotNull ConstructorFilter<Type> copy();

    @NotNull ConstructorFilter<Type> ofType(Class<? extends Type> type, @NotNull TypeComparisonBy comparisonBy);

    default @NotNull ConstructorFilter<Type> ofType(Class<? extends Type> type) {
        return this.ofType(type, TypeComparisonBy.Assignability);
    }
}
