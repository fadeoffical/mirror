package fade.mirror.internal.impl.filter;

import fade.mirror.MConstructor;
import fade.mirror.MMethod;
import fade.mirror.MParameter;
import fade.mirror.filter.Filter;
import fade.mirror.filter.MethodFilter;
import fade.mirror.filter.RewriteOperation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Basic implementation of {@link MethodFilter}.
 *
 * @author fade
 */
public final class BasicMethodFilter<T>
        implements MethodFilter<T> {

    /**
     * The parameter types to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable List<Class<?>> parameterTypes;

    /**
     * The annotations to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable List<Class<? extends Annotation>> annotations;

    /**
     * The name to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable String name;

    /**
     * The return type to filter by. If {@code null}, no filtering will be done.
     */
    private @Nullable Class<?> returnType;

    /**
     * Creates a new {@link BasicMethodFilter}.
     */
    private BasicMethodFilter() {
        super();
    }

    /**
     * Creates a new {@link BasicMethodFilter} with the given parameters.
     *
     * @param parameterTypes The parameter types to filter by.
     * @param annotations    The annotations to filter by.
     * @param name           The name to filter by.
     * @param returnType     The return type to filter by.
     */
    private BasicMethodFilter(@Nullable List<Class<?>> parameterTypes, @Nullable List<Class<? extends Annotation>> annotations, @Nullable String name, @Nullable Class<?> returnType) {
        this.parameterTypes = parameterTypes;
        this.annotations = annotations;
        this.name = name;
        this.returnType = returnType;
    }

    /**
     * Creates a new {@link BasicMethodFilter}. This method should not be used directly. Instead, use
     * {@link Filter#forMethods()}.
     *
     * @return The new {@link BasicMethodFilter}.
     */
    public static <T> @NotNull MethodFilter<T> create() {
        return new BasicMethodFilter<>();
    }

    @Override
    public boolean test(MMethod<T> method) {
        if (this.name != null && !method.getName().equals(this.name)) return false;
        if (this.parameterTypes != null && !method.getParameters()
                .map(MParameter::getType)
                .allMatch(parameterType -> this.parameterTypes.stream().anyMatch(parameterType::isAssignableFrom)))
            return false;

        if (this.annotations != null && (method.getAnnotations().findAny().isEmpty() || !method.getAnnotations()
                .allMatch(annotation -> this.annotations.stream()
                        .anyMatch(annotationType -> annotationType.isAssignableFrom(annotation.getClass())))))
            return false;
        return this.returnType == null || this.returnType.isAssignableFrom(method.getReturnType());
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <C> MethodFilter<C> ofType(@NotNull Class<C> type) {
        this.returnType = type;
        return (MethodFilter<C>) this;
    }

    @Override
    public @NotNull MethodFilter<T> withNoAnnotations() {
        this.annotations = new ArrayList<>(0);
        return this;
    }

    @Override
    public @NotNull MethodFilter<T> withAnnotations(@NotNull List<Class<? extends Annotation>> annotations, @NotNull RewriteOperation operation) {
        if (this.annotations == null) this.annotations = new ArrayList<>(annotations.size());
        operation.apply(this.annotations, annotations);
        return this;
    }

    @Override
    public @NotNull MethodFilter<T> withAnnotations(@NotNull List<Class<? extends Annotation>> annotations) {
        return this.withAnnotations(annotations, RewriteOperation.Append);
    }

    @Override
    public @NotNull MethodFilter<T> withAnnotation(@NotNull Class<? extends Annotation> annotation, @NotNull RewriteOperation operation) {
        return this.withAnnotations(List.of(annotation), operation);
    }

    @Override
    public @NotNull MethodFilter<T> withAnnotation(@NotNull Class<? extends Annotation> annotation) {
        return this.withAnnotation(annotation, RewriteOperation.Append);
    }

    @Override
    public @NotNull MethodFilter<T> withNoParameters() {
        this.parameterTypes = new ArrayList<>(0);
        return this;
    }

    @Override
    public @NotNull MethodFilter<T> withParameters(@NotNull List<Class<?>> parameterTypes, @NotNull RewriteOperation operation) {
        if (this.parameterTypes == null) this.parameterTypes = new ArrayList<>(parameterTypes.size());
        operation.apply(this.parameterTypes, parameterTypes);
        return this;
    }

    @Override
    public @NotNull MethodFilter<T> withParameters(@NotNull List<Class<?>> parameterTypes) {
        return this.withParameters(parameterTypes, RewriteOperation.Append);
    }

    @Override
    public @NotNull MethodFilter<T> withParameter(@NotNull Class<?> parameterType, @NotNull RewriteOperation operation) {
        return this.withParameters(List.of(parameterType), operation);
    }

    @Override
    public @NotNull Predicate<MConstructor<fade.mirror.mock.MockClass>> withParameter(@NotNull Class<?> parameterType) {
        return this.withParameter(parameterType, RewriteOperation.Append);
    }

    @Override
    public @NotNull MethodFilter<T> withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull MethodFilter<T> copy() {
        return new BasicMethodFilter<>(this.parameterTypes, this.annotations, this.name, this.returnType);
    }
}
