package fade.mirror.internal.impl.filter;

import fade.mirror.MConstructor;
import fade.mirror.filter.ConstructorFilter;
import fade.mirror.filter.criterion.AnnotationCriterion;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public final class ConstructorFilterImpl<Type>
        extends BaseFilter<MConstructor<Type>>
        implements ConstructorFilter<Type> {

    private ConstructorFilterImpl() {
        super();
    }

    @Override
    public @NotNull <AnnotationType extends Annotation> ConstructorFilter<Type> withAnnotation(@NotNull AnnotationCriterion<AnnotationType> annotationFilter) {
        return this;
    }

    @Override
    @Contract(pure = true)
    public @NotNull ConstructorFilter<Type> copy() {
        return create();
    }

    @ApiStatus.Internal
    public static <Type> ConstructorFilterImpl<Type> create() {
        return new ConstructorFilterImpl<>();
    }

}
