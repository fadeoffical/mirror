package fade.mirror.internal.impl.filter;

import fade.mirror.MConstructor;
import fade.mirror.MParameter;
import fade.mirror.filter.ConstructorFilter;
import fade.mirror.filter.Filter;
import fade.mirror.filter.RewriteOperation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

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
    private @Nullable List<Class<?>> parameterTypes;

    /**
     * The annotations to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable List<Class<? extends Annotation>> annotations;

    /**
     * Creates a new {@link BasicConstructorFilter}.
     */
    private BasicConstructorFilter() {
        super();
    }

    private BasicConstructorFilter(@Nullable List<Class<?>> parameterTypes, @Nullable List<Class<? extends Annotation>> annotations) {
        this.parameterTypes = parameterTypes;
        this.annotations = annotations;
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
    public @NotNull ConstructorFilter withNoParameters() {
        this.parameterTypes = new ArrayList<>(0);
        return this;
    }

    @Override
    public @NotNull ConstructorFilter withParameters(@NotNull List<Class<?>> parameterTypes, @NotNull RewriteOperation operation) {
        if (this.parameterTypes == null) this.parameterTypes = new ArrayList<>(parameterTypes.size());
        operation.apply(this.parameterTypes, parameterTypes);
        return this;
    }

    @Override
    public @NotNull ConstructorFilter withParameters(@NotNull List<Class<?>> parameterTypes) {
        return this.withParameters(parameterTypes, RewriteOperation.Append);
    }

    @Override
    public @NotNull ConstructorFilter withParameter(@NotNull Class<?> parameterType, @NotNull RewriteOperation operation) {
        return this.withParameters(List.of(parameterType), operation);
    }

    @Override
    public @NotNull ConstructorFilter withParameter(@NotNull Class<?> parameterType) {
        return this.withParameter(parameterType, RewriteOperation.Append);
    }

    @Override
    public @NotNull ConstructorFilter withNoAnnotations() {
        this.annotations = new ArrayList<>(0);
        return this;
    }

    @Override
    public @NotNull ConstructorFilter withAnnotations(@NotNull List<Class<? extends Annotation>> annotations, @NotNull RewriteOperation operation) {
        if (this.annotations == null) this.annotations = new ArrayList<>(annotations.size());
        operation.apply(this.annotations, annotations);
        return this;
    }

    @Override
    public @NotNull ConstructorFilter withAnnotations(@NotNull List<Class<? extends Annotation>> annotations) {
        return this.withAnnotations(annotations, RewriteOperation.Append);
    }

    @Override
    public @NotNull ConstructorFilter withAnnotation(@NotNull Class<? extends Annotation> annotation, @NotNull RewriteOperation operation) {
        return this.withAnnotations(List.of(annotation), operation);
    }

    @Override
    public @NotNull ConstructorFilter withAnnotation(@NotNull Class<? extends Annotation> annotation) {
        return this.withAnnotation(annotation, RewriteOperation.Append);
    }

    @Override
    public boolean test(MConstructor<?> constructor) {
        if (this.parameterTypes != null && !constructor.getParameters()
                .map(MParameter::getType)
                .allMatch(parameterType -> this.parameterTypes.stream().anyMatch(parameterType::isAssignableFrom)))
            return false;
        return this.annotations != null && (constructor.getAnnotations().findAny().isEmpty() || constructor.getAnnotations()
                .allMatch(annotation -> this.annotations.stream()
                        .anyMatch(clazz -> clazz.isAssignableFrom(annotation.annotationType()))));
    }

    @Override
    public @NotNull ConstructorFilter copy() {
        return new BasicConstructorFilter(this.parameterTypes, this.annotations);
    }
}
