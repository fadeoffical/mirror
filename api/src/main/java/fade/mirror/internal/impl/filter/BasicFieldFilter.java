package fade.mirror.internal.impl.filter;

import fade.mirror.MField;
import fade.mirror.filter.FieldFilter;
import fade.mirror.filter.Filter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

/**
 * Basic implementation of {@link FieldFilter}.
 *
 * @author fade
 */
public final class BasicFieldFilter
        implements FieldFilter {

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

    /**
     * Creates a new {@link BasicFieldFilter}.
     */
    private BasicFieldFilter() {
        super();
    }

    /**
     * Creates a new {@link BasicFieldFilter} with the given parameters.
     *
     * @param annotations The annotations to filter by.
     * @param name        The name to filter by.
     * @param type        The type to filter by.
     */
    private BasicFieldFilter(Annotation @Nullable [] annotations, @Nullable String name, @Nullable Class<?> type) {
        this.annotations = annotations == null ? null : annotations.clone();
        this.name = name;
        this.type = type;
    }

    /**
     * Creates a new {@link BasicFieldFilter}. This method should not be used directly. Instead, use
     * {@link Filter#forFields()}.
     *
     * @return The new {@link BasicFieldFilter}.
     */
    public static FieldFilter create() {
        return new BasicFieldFilter();
    }

    @Override
    public @NotNull FieldFilter withAnnotations(@NotNull Annotation... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    @Override
    public @NotNull FieldFilter clearAnnotations() {
        this.annotations = null;
        return this;
    }

    @Override
    public @NotNull FieldFilter ofType(@NotNull Class<?> type) {
        this.type = type;
        return this;
    }

    @Override
    public @NotNull FieldFilter clearType() {
        this.type = null;
        return this;
    }

    @Override
    public @NotNull FieldFilter withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull FieldFilter clearName() {
        this.name = null;
        return this;
    }

    @Override
    public boolean test(MField<?> field) {
        if (this.name != null && !field.getName().equals(this.name)) return false;
        if (this.annotations != null && !field.getAnnotations()
                .allMatch(annotation -> FilterUtil.isAnnotationOneOfRequired(this.annotations, annotation)))
            return false;
        return this.type == null || this.type.isAssignableFrom(field.getType());
    }

    @Override
    public @NotNull FieldFilter copy() {
        return new BasicFieldFilter(this.annotations, this.name, this.type);
    }
}
