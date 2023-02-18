package fade.mirror.internal.impl.filter;

import fade.mirror.MConstructor;
import fade.mirror.MParameter;
import fade.mirror.filter.ConstructorFilter;
import fade.mirror.filter.Filter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;

public final class BasicConstructorFilter
        extends Filter<MConstructor<?>>
        implements ConstructorFilter {

    private Class<?> @Nullable [] parameterTypes;
    private Annotation @Nullable [] annotations;

    private BasicConstructorFilter() {
    }

    public static @NotNull BasicConstructorFilter create() {
        return new BasicConstructorFilter();
    }

    @Override
    public @NotNull ConstructorFilter withParameters(@NotNull Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes.clone();
        return this;
    }

    @Override
    public @NotNull ConstructorFilter clearParameters() {
        this.parameterTypes = null;
        return this;
    }

    @Override
    public @NotNull ConstructorFilter withAnnotations(@NotNull Annotation... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    @Override
    public @NotNull ConstructorFilter clearAnnotations() {
        this.annotations = null;
        return this;
    }

    @Override
    public boolean test(MConstructor<?> constructor) {
        if (this.parameterTypes != null && !constructor.getParameters()
                .map(MParameter::getType)
                .allMatch(this::testAllMatch0)) return false;
        return this.annotations == null || constructor.getAnnotations().allMatch(this::testAllMatch1);
    }
}
