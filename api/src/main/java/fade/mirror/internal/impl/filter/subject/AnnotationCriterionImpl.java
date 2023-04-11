package fade.mirror.internal.impl.filter.subject;

import fade.mirror.filter.TypeComparisonBy;
import fade.mirror.filter.criterion.AnnotationCriterion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

public class AnnotationCriterionImpl<Type extends Annotation>
        implements AnnotationCriterion<Type> {

    private @Nullable Class<? extends Type> type;
    private TypeComparisonBy operation;

    private AnnotationCriterionImpl() {
        this.type = null;
        this.operation = TypeComparisonBy.Equality;
    }

    public static <Type extends Annotation> AnnotationCriterion<Type> create() {
        return new AnnotationCriterionImpl<>();
    }

    @Override
    public @NotNull AnnotationCriterion<Type> compareTypesBy(@NotNull TypeComparisonBy by) {
        this.operation = by;
        return this;
    }

    @Override
    public @NotNull AnnotationCriterion<Type> ofType(@NotNull Class<? extends Type> type) {
        this.type = type;
        return this;
    }

    @Override
    public boolean isEmpty() {
        return this.type == null;
    }

    @Override
    public boolean test(Class<? extends Type> type) {
        return this.type != null && this.operation.compare(this.type, type);
    }
}
