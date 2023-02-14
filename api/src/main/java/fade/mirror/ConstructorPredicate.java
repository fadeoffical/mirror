package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public class ConstructorPredicate {

    private final Class<?>[] types;
    private final Annotation[] annotations;

    private ConstructorPredicate(@NotNull Class<?>[] types, @NotNull Annotation[] annotations) {
        this.types = types;
        this.annotations = annotations;
    }

    private ConstructorPredicate(@NotNull Class<?>[] types) {
        this(types, new Annotation[]{});
    }

    private ConstructorPredicate(@NotNull Annotation[] annotation) {
        this(new Class[]{}, annotation);
    }

    public static @NotNull ConstructorPredicate withTypes(@NotNull Class<?>... types) {
        return new ConstructorPredicate(types);
    }

    public static @NotNull ConstructorPredicate withAnnotations(@NotNull Annotation... annotations) {
        return new ConstructorPredicate(annotations);
    }
}
