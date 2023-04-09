package fade.mirror.internal.impl.filter;

import fade.mirror.MParameter;
import fade.mirror.filter.ParameterFilter;
import fade.mirror.filter.RewriteOperation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

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
    private BasicParameterFilter(@Nullable List<Class<? extends Annotation>> annotations, @Nullable String name, @Nullable Class<?> type) {
        super();
        this.annotations = annotations;
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
    public boolean test(MParameter<?> parameter) {
        if (this.name != null && !parameter.getName().equals(this.name)) return false;


        if (this.annotations != null) {
            boolean hasNoParameters = parameter.getAnnotations().findAny().isEmpty();
            boolean noneMatch = parameter.getAnnotations().map(Annotation::annotationType).noneMatch(this.annotations::contains);

            if (hasNoParameters || noneMatch) return false;
        }

        return this.type == null || this.type.isAssignableFrom(parameter.getType());
    }

    @Override
    public @NotNull ParameterFilter copy() {
        return new BasicParameterFilter(this.annotations, this.name, this.type);
    }

    @Override
    public @NotNull ParameterFilter withNoAnnotations() {
        this.annotations = new ArrayList<>(0);
        return this;
    }

    @Override
    public @NotNull ParameterFilter withAnnotations(@NotNull List<Class<? extends Annotation>> annotations, @NotNull RewriteOperation operation) {
        if (this.annotations == null) this.annotations = new ArrayList<>(annotations.size());
        operation.apply(this.annotations, annotations);
        return this;
    }

    @Override
    public @NotNull <C> ParameterFilter ofType(Class<C> type) {
        this.type = type;
        return this;
    }

    @Override
    public @NotNull ParameterFilter withName(@NotNull String name) {
        this.name = name;
        return this;
    }
}
