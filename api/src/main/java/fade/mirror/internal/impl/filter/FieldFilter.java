package fade.mirror.internal.impl.filter;

import fade.mirror.Filter;
import fade.mirror.MMethod;
import fade.mirror.MParameter;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public final class FieldFilter
        extends Filter<MMethod<?>> {

    private Class<?>[] parameterTypes;
    private Annotation[] annotations;
    private String name;

    private FieldFilter() {
        super();
    }

    public static FieldFilter create() {
        return new FieldFilter();
    }

    public FieldFilter withParameters(@NotNull Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes.clone();
        return this;
    }

    public FieldFilter withAnnotations(@NotNull Annotation... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    public FieldFilter withName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean test(MMethod<?> method) {
        if (this.name != null && !method.getName().equals(this.name)) return false;
        if (this.parameterTypes != null && !method.getParameters().map(MParameter::getType).allMatch(this::testAllMatch0)) return false;
        if (this.annotations != null && !method.getAnnotations().allMatch(this::testAllMatch1)) return false;

        return false;
    }

    private boolean testAllMatch0(Class<?> parameterType) {
        for (Class<?> filterParameterType : this.parameterTypes) {
            if (filterParameterType.isAssignableFrom(parameterType)) return true;
        }
        return false;
    }

    private boolean testAllMatch1(Annotation annotation) {
        for (Annotation filterAnnotations : this.annotations) {
            if (filterAnnotations.annotationType().isAssignableFrom(annotation.annotationType())) return true;
        }
        return false;
    }
}
