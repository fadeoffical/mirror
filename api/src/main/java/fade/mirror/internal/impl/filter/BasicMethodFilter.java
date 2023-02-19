package fade.mirror.internal.impl.filter;

import fade.mirror.MMethod;
import fade.mirror.MParameter;
import fade.mirror.filter.Filter;
import fade.mirror.filter.MethodFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

/**
 * Basic implementation of {@link MethodFilter}.
 *
 * @author fade
 */
public final class BasicMethodFilter
        extends Filter<MMethod<?>>
        implements MethodFilter {

    /**
     * The parameter types to filter by. If {@code null}, no filtering will be done.
     */
    private Class<?> @Nullable [] parameterTypes;

    /**
     * The annotations to filter by. If {@code null}, no filtering will be done.
     */
    private Annotation @Nullable [] annotations;

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
    private BasicMethodFilter(Class<?> @Nullable [] parameterTypes, Annotation @Nullable [] annotations, @Nullable String name, @Nullable Class<?> returnType) {
        this.parameterTypes = parameterTypes == null ? null : parameterTypes.clone();
        this.annotations = annotations == null ? null : annotations.clone();
        this.name = name;
        this.returnType = returnType;
    }

    /**
     * Creates a new {@link BasicMethodFilter}. This method should not be used directly. Instead, use
     * {@link Filter#forMethods()}.
     *
     * @return The new {@link BasicMethodFilter}.
     */
    public static BasicMethodFilter create() {
        return new BasicMethodFilter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull BasicMethodFilter withParameters(@NotNull Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes.clone();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MethodFilter clearParameters() {
        this.parameterTypes = null;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull BasicMethodFilter withAnnotations(@NotNull Annotation... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MethodFilter clearAnnotations() {
        this.annotations = null;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MethodFilter withReturnType(@NotNull Class<?> returnType) {
        this.returnType = returnType;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MethodFilter clearReturnType() {
        this.returnType = null;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull BasicMethodFilter withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MethodFilter clearName() {
        this.name = null;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean test(MMethod<?> method) {
        if (this.name != null && !method.getName().equals(this.name)) return false;
        if (this.parameterTypes != null && !method.getParameters()
                .map(MParameter::getType)
                .allMatch(parameterType -> FilterUtil.isParameterOneOfRequired(this.parameterTypes, parameterType)))
            return false;
        if (this.annotations != null && !method.getAnnotations()
                .allMatch(annotation -> FilterUtil.isAnnotationOneOfRequired(this.annotations, annotation)))
            return false;
        return this.returnType == null || this.returnType.isAssignableFrom(method.getReturnType());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MethodFilter copy() {
        return new BasicMethodFilter(this.parameterTypes, this.annotations, this.name, this.returnType);
    }
}
