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
 * @param <T> The type of the field.
 * @author fade
 */
public final class BasicFieldFilter<T>
        implements FieldFilter<T> {

    /**
     * The annotations to filter by. If {@code null}, no filtering will be done.
     */
    private Class<? extends Annotation> @Nullable [] annotations;

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
    private BasicFieldFilter(Class<? extends Annotation> @Nullable [] annotations, @Nullable String name, @Nullable Class<?> type) {
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
    public static FieldFilter<?> create() {
        return new BasicFieldFilter<>();
    }

    @Override
    public @NotNull FieldFilter<T> withAnnotations(@NotNull Class<? extends Annotation>... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    @Override
    public @NotNull FieldFilter<T> clearAnnotations() {
        this.annotations = null;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> @NotNull FieldFilter<C> ofType(@NotNull Class<C> type) {
        this.type = type;
        return (FieldFilter<C>) this;
    }

    @Override
    public @NotNull FieldFilter<T> clearType() {
        this.type = null;
        return this;
    }

    @Override
    public @NotNull FieldFilter<T> withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull FieldFilter<T> clearName() {
        this.name = null;
        return this;
    }

    @Override
    public boolean test(MField<T> field) {
        if (this.name != null && !field.getName().equals(this.name)) return false;
        if (this.annotations != null && !field.getAnnotations()
                .allMatch(annotation -> FilterUtil.isAnnotationOneOfRequired(this.annotations, annotation)))
            return false;
        return this.type == null || this.type.isAssignableFrom(field.getType());
    }

    @Override
    public @NotNull FieldFilter<T> copy() {
        return new BasicFieldFilter<>(this.annotations, this.name, this.type);
    }
}
