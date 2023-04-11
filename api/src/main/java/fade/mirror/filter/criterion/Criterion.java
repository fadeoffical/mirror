package fade.mirror.filter.criterion;

import fade.mirror.internal.impl.filter.subject.AnnotationCriterionImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

public interface Criterion<Type>
        extends Predicate<Type> {


    static <Type extends Annotation> @NotNull AnnotationCriterion<Type> forAnnotation() {
        return AnnotationCriterionImpl.create();
    }

    /**
     * Returns whether this criterion is empty. <br/> A criterion is empty if it does not contain any .
     *
     * @return {@code true} if this filter is empty, {@code false} otherwise.
     */
    @Contract(pure = true)
    boolean isEmpty();

}
