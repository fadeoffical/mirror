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
                .allMatch(this::testAllMatch0)) return false;
        if (this.annotations != null && !method.getAnnotations().allMatch(this::testAllMatch1)) return false;

        return false;
    }

    private boolean testAllMatch0(Class<?> parameterType) {
        // already checked for null
        if (this.parameterTypes == null) return true;

        for (Class<?> filterParameterType : this.parameterTypes) {
            if (filterParameterType.isAssignableFrom(parameterType)) return true;
        }
        return false;
    }

    private boolean testAllMatch1(Annotation annotation) {
        // already checked for null
        if (this.annotations == null) return true;

        for (Annotation filterAnnotations : this.annotations) {
            if (filterAnnotations.annotationType().isAssignableFrom(annotation.annotationType())) return true;
        }
        return false;
    }
}
