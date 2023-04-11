package fade.mirror.filter;

import fade.mirror.MConstructor;
import fade.mirror.filter.criterion.AnnotationCriterion;
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

    <AnnotationType extends Annotation> @NotNull ConstructorFilter<Type> withAnnotation(@NotNull AnnotationCriterion<AnnotationType> annotationFilter);


    @Override
    @NotNull ConstructorFilter<Type> copy();
}
