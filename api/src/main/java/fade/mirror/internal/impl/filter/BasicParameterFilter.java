package fade.mirror.internal.impl.filter;

import fade.mirror.MParameter;
import fade.mirror.filter.Filter;
import fade.mirror.filter.ParameterFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

public final class BasicParameterFilter
        extends Filter<MParameter<?>>
        implements ParameterFilter {

    /**
     * The annotations to filter by. If {@code null}, no filtering will be done.
     */
    private Annotation @Nullable [] annotations;

    /**
     * The name to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable String name;

    /**
     * The type to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable Class<?> type;

    private BasicParameterFilter() {
        super();
    }

    private BasicParameterFilter(Annotation @Nullable [] annotations, @Nullable String name, @Nullable Class<?> type) {
        super();
        this.annotations = annotations == null ? null : annotations.clone();
        this.name = name;
        this.type = type;
    }

    public static ParameterFilter create() {
        return new BasicParameterFilter();
    }

    @Override
    public @NotNull ParameterFilter withAnnotations(@NotNull Annotation... annotations) {
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
