package fade.mirror.internal.impl.filter;

import fade.mirror.MConstructor;
import fade.mirror.MParameter;
import fade.mirror.filter.ConstructorFilter;
import fade.mirror.filter.Filter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

/**
 * Basic implementation of {@link ConstructorFilter}.
 *
 * @author fade
 */
public final class BasicConstructorFilter
        implements ConstructorFilter {

    /**
     * The parameter types to filter by. If {@code null}, no filtering will be done.
     */
    private Class<?> @Nullable [] parameterTypes;

    /**
     * The annotations to filter by. If {@code null}, no filtering will be done.
     */
    private Annotation @Nullable [] annotations;

    /**
     * Creates a new {@link BasicConstructorFilter}.
     */
    private BasicConstructorFilter() {
        super();
    }

    private BasicConstructorFilter(Class<?> @Nullable [] parameterTypes, Annotation @Nullable [] annotations) {
        this.parameterTypes = parameterTypes == null ? null : parameterTypes.clone();
        this.annotations = annotations == null ? null : annotations.clone();
    }

    /**
     * Creates a new {@link BasicConstructorFilter}. This method should not be used directly. Instead, use
     * {@link Filter#forConstructors()}.
     *
     * @return The new {@link BasicConstructorFilter}.
     */
    public static @NotNull BasicConstructorFilter create() {
        return new BasicConstructorFilter();
    }

    @Override
    public @NotNull ConstructorFilter withParameters(@NotNull Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes.clone();
        return this;
    }

    @Override
    public @NotNull ConstructorFilter clearParameters() {
        this.parameterTypes = null;
        return this;
    }

    @Override
    public @NotNull ConstructorFilter withAnnotations(@NotNull Annotation... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    @Override
    public @NotNull ConstructorFilter clearAnnotations() {
        this.annotations = null;
        return this;
    }

    @Override
    public boolean test(MConstructor<?> constructor) {
        if (this.parameterTypes != null && !constructor.getParameters()
                .map(MParameter::getType)
                .allMatch(parameterType -> FilterUtil.isParameterOneOfRequired(this.parameterTypes, parameterType)))
            return false;
        return this.annotations == null || constructor.getAnnotations()
                .allMatch(annotation -> FilterUtil.isAnnotationOneOfRequired(this.annotations, annotation));
    }

    @Override
    public @NotNull ConstructorFilter copy() {
        return new BasicConstructorFilter(this.parameterTypes, this.annotations);
    }
}
