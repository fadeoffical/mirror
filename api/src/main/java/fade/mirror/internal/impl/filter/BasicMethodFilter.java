package fade.mirror.internal.impl.filter;

import fade.mirror.MMethod;
import fade.mirror.MParameter;
import fade.mirror.filter.Filter;
import fade.mirror.filter.MethodFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

public final class BasicMethodFilter
        extends Filter<MMethod<?>>
        implements MethodFilter {

    private Class<?> @Nullable [] parameterTypes;
    private Annotation @Nullable [] annotations;
    private @Nullable String name;
    private @Nullable Class<?> returnType;

    private BasicMethodFilter() {
        super();
    }

    public static BasicMethodFilter create() {
        return new BasicMethodFilter();
    }

    @Override
    public @NotNull BasicMethodFilter withParameters(@NotNull Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes.clone();
        return this;
    }

    @Override
    public @NotNull MethodFilter clearParameters() {
        this.parameterTypes = null;
        return this;
    }

    @Override
    public @NotNull BasicMethodFilter withAnnotations(@NotNull Annotation... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    @Override
    public @NotNull MethodFilter clearAnnotations() {
        this.annotations = null;
        return this;
    }

    @Override
    public @NotNull MethodFilter withReturnType(@NotNull Class<?> returnType) {
        this.returnType = returnType;
        return this;
    }

    @Override
    public @NotNull MethodFilter clearReturnType() {
        this.returnType = null;
        return this;
    }

    @Override
    public @NotNull BasicMethodFilter withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull MethodFilter clearName() {
        this.name = null;
        return this;
    }

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
}
