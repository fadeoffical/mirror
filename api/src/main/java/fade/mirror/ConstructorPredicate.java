package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

public final class ConstructorPredicate implements Predicate<ConstructorAccessor<?>> {

    private final Class<?>[] types;
    private Annotation[] annotations;

    private ConstructorPredicate(@NotNull Class<?>[] types) {
        this.types = types;
    }

    public static @NotNull ConstructorPredicate withTypes(@NotNull Class<?>... types) {
        return new ConstructorPredicate(types);
    }

    public @NotNull ConstructorPredicate andAnnotations(@NotNull Annotation... annotations) {
        this.annotations = annotations.clone();
        return this;
    }

    @Override
    public boolean test(ConstructorAccessor<?> accessor) {
        return accessor.areParametersEqual(this.types) && (this.annotations == null || accessor.areAnnotationsEqual(this.annotations));
    }
}
