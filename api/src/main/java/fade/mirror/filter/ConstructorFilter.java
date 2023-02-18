package fade.mirror.filter;

import fade.mirror.MConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

public final class ConstructorFilter implements Predicate<MConstructor<?>> {

    private Class<?>[] parameterTypes;
    private Annotation[] annotations;

    private ConstructorFilter() {}

    public static @NotNull ConstructorFilter create() {
        return new ConstructorFilter();
    }

    public @NotNull ConstructorFilter withParameters(@NotNull Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes.clone();
        return this;
    }

    public @NotNull ConstructorFilter andAnnotations(@NotNull Annotation... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    @Override
    public boolean test(@NotNull MConstructor<?> constructor) {
        // todo: implement
        return false;
    }
}
