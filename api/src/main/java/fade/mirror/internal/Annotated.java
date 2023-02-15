package fade.mirror.internal;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public interface Annotated {

    boolean areAnnotationsPresent(@NotNull Annotation[] annotations);

    boolean isAnnotationPresent(@NotNull Annotation annotation);
}
