package fade.mirror.internal.impl.filter;

import fade.mirror.MField;
import fade.mirror.filter.FieldFilter;
import fade.mirror.filter.Filter;
import fade.mirror.filter.RewriteOperation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of {@link FieldFilter}.
 *
 * @param <Type> The type of the field.
 * @author fade
 */
public final class BasicFieldFilter<Type>
        implements FieldFilter<Type> {

    /**
     * The annotations to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable List<Class<? extends Annotation>> annotations;

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
    private BasicFieldFilter(@Nullable List<Class<? extends Annotation>> annotations, @Nullable String name, @Nullable Class<?> type) {
        this.annotations = annotations;
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
    public @NotNull FieldFilter<Type> withNoAnnotations() {
        this.annotations = new ArrayList<>(0);
        return this;
    }

    @Override
    public @NotNull FieldFilter<Type> withAnnotations(@NotNull List<Class<? extends Annotation>> annotations, @NotNull RewriteOperation operation) {
        if (this.annotations == null) this.annotations = new ArrayList<>(annotations.size());
        operation.apply(this.annotations, annotations);
        this.annotations.addAll(annotations);

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <ClassType> @NotNull FieldFilter<ClassType> ofType(@NotNull Class<ClassType> type) {
        this.type = type;
        return (FieldFilter<ClassType>) this;
    }

    @Override
    public @NotNull FieldFilter<Type> withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean test(MField<Type> field) {
        if (this.name != null && !field.getName().equals(this.name)) return false;
        if (this.annotations != null && (field.getAnnotations().findAny().isEmpty() || !field.getAnnotations()
                .allMatch(annotation -> this.annotations.stream()
                        .anyMatch(clazz -> clazz.isAssignableFrom(annotation.annotationType())))))
            return false;
        return this.type == null || this.type.isAssignableFrom(field.getType());
    }

    @Override
    public @NotNull FieldFilter<Type> copy() {
        return new BasicFieldFilter<>(this.annotations, this.name, this.type);
    }
}
