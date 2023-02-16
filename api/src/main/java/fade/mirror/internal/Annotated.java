package fade.mirror.internal;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Annotated {

    @NotNull Stream<Annotation> getAnnotations();

    @NotNull Stream<Annotation> getAnnotations(@NotNull Predicate<Annotation> filter);

    @NotNull Optional<Annotation> getAnnotation(@NotNull Predicate<Annotation> filter);

    @SuppressWarnings("unchecked") // implementations are REQUIRED to use the varargs in a safe manner!
    boolean isAnnotatedWith(@NotNull Class<? extends Annotation>... annotations);

    @NotNull Optional<Annotation> getAnnotationOfType(@NotNull Class<? extends Annotation> type);

    boolean isAnnotated();

    int getAnnotationCount();
}
