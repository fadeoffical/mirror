package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Annotated {

    @NotNull Stream<Annotation> getAnnotations();

    @NotNull Stream<Annotation> getAnnotations(@NotNull Predicate<Annotation> filter);

    @NotNull Optional<Annotation> getAnnotation(@NotNull Predicate<Annotation> filter);

    boolean isAnnotatedWith(@NotNull Class<? extends Annotation>[] annotations);

    boolean isAnnotatedWith(@NotNull Class<? extends Annotation> annotation);

    <T extends Annotation> @NotNull Optional<T> getAnnotationOfType(@NotNull Class<T> type);

    boolean isAnnotated();

    int getAnnotationCount();
}
