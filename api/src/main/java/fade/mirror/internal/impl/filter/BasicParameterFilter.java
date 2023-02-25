package fade.mirror.internal.impl.filter;

import fade.mirror.MParameter;
import fade.mirror.filter.ParameterFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

/**
 * Basic implementation of {@link ParameterFilter}.
 *
 * @author fade
 */
public final class BasicParameterFilter
        implements ParameterFilter {

    /**
     * The annotations to filter by. If {@code null}, no filtering will be done.
     */
    private Class<?extends Annotation> @Nullable [] annotations;

    /**
     * The name to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable String name;

    /**
     * The type to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable Class<?> type;

    /**
     * Creates a new {@link BasicParameterFilter}.
     */
    private BasicParameterFilter() {
        super();
    }

    /**
     * Creates a new {@link BasicParameterFilter} with the given parameters.
     *
     * @param annotations The annotations to filter by.
     * @param name        The name to filter by.
     * @param type        The type to filter by.
     */
    private BasicParameterFilter(Class<?extends Annotation> @Nullable [] annotations, @Nullable String name, @Nullable Class<?> type) {
        super();
        this.annotations = annotations == null ? null : annotations.clone();
        this.name = name;
        this.type = type;
    }

    /**
     * Creates a new {@link BasicParameterFilter}.
     *
     * @return The created {@link BasicParameterFilter}.
     */
    public static ParameterFilter create() {
        return new BasicParameterFilter();
    }

    @Override
    public @NotNull ParameterFilter withAnnotations(@NotNull Class<?extends Annotation>... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    @Override
    public @NotNull ParameterFilter clearAnnotations() {
        this.annotations = null;
        return this;
    }

    @Override
    public @NotNull ParameterFilter ofType(@NotNull Class<?> type) {
        this.type = type;
        return this;
    }

    @Override
    public @NotNull ParameterFilter clearType() {
        this.type = null;
        return this;
    }

    @Override
    public @NotNull ParameterFilter withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull ParameterFilter clearName() {
        this.name = null;
        return this;
    }

    @Override
    public boolean test(MParameter<?> parameter) {
        if (this.name != null && !parameter.getName().equals(this.name)) return false;
        if (this.annotations != null && !parameter.getAnnotations()
                .allMatch(annotation -> FilterUtil.isAnnotationOneOfRequired(this.annotations, annotation)))
            return false;
        return this.type == null || this.type.isAssignableFrom(parameter.getType());
    }

    @Override
    public @NotNull ParameterFilter copy() {
        return new BasicParameterFilter(this.annotations, this.name, this.type);
    }
}
