package fade.mirror.filter.criterion;

import fade.mirror.filter.TypeComparisonBy;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public interface AnnotationCriterion<Type extends Annotation>
        extends Criterion<Class<? extends Type>> {

    /**
     * Sets the comparison operation to be performed on the type of the annotation.
     *
     * @param operation The comparison operation to be performed.
     * @return This filter.
     */
    @NotNull AnnotationCriterion<Type> compareTypesBy(@NotNull TypeComparisonBy operation);

    /**
     * Sets the type of the annotation to be filtered.
     *
     * @param type The type of the annotation to be filtered.
     * @return This filter.
     */
    @NotNull AnnotationCriterion<Type> ofType(@NotNull Class<? extends Type> type);

}
