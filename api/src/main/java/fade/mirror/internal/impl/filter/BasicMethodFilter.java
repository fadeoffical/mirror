package fade.mirror.internal.impl.filter;

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

/**
 * Basic implementation of {@link MethodFilter}.
 *
 * @author fade
 */
public final class BasicMethodFilter<Type>
        implements MethodFilter<Type> {

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
    public static <Type> MethodFilter<Type> create() {
        return new BasicMethodFilter<>();
    }

    @Override
    public boolean test(MMethod<Type> method) {
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
    public @NotNull <ClassType> MethodFilter<ClassType> ofType(@NotNull Class<ClassType> type) {
        this.returnType = type;
        return (MethodFilter<ClassType>) this;
    }

    @Override
    public @NotNull MethodFilter<Type> withNoAnnotations() {
        this.annotations = new ArrayList<>(0);
        return this;
    }

    @Override
    public @NotNull MethodFilter<Type> withAnnotations(@NotNull List<Class<? extends Annotation>> annotations, @NotNull RewriteOperation operation) {
        if (this.annotations == null) this.annotations = new ArrayList<>(annotations.size());
        operation.apply(this.annotations, annotations);
        return this;
    }

    @Override
    public @NotNull MethodFilter<Type> withNoParameters() {
        this.parameterTypes = new ArrayList<>(0);
        return this;
    }

    @Override
    public @NotNull MethodFilter<Type> withParameters(@NotNull List<Class<?>> parameterTypes, @NotNull RewriteOperation operation) {
        if (this.parameterTypes == null) this.parameterTypes = new ArrayList<>(parameterTypes.size());
        operation.apply(this.parameterTypes, parameterTypes);
        return this;
    }

    @Override
    public @NotNull MethodFilter<Type> withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull MethodFilter<Type> copy() {
        return new BasicMethodFilter<>(this.parameterTypes, this.annotations, this.name, this.returnType);
    }
}
