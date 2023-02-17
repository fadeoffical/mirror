package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface Parameterized {

    @NotNull Stream<MParameter<?>> getParameters();

    @NotNull Stream<MParameter<?>> getParameters(@NotNull Predicate<MParameter<?>> filter);

    @NotNull Optional<MParameter<?>> getParameter(@NotNull Predicate<MParameter<?>> filter);

    <T> @NotNull Stream<MParameter<T>> getParametersOfType(@NotNull Class<T> type);

    <T> @NotNull Optional<MParameter<T>> getParameterOfType(@NotNull Class<T> type);

    @NotNull Stream<MParameter<?>> getParametersWithAnnotations(@NotNull Class<? extends Annotation>[] annotations);

    @NotNull Optional<MParameter<?>> getParameterWithAnnotations(@NotNull Class<? extends Annotation>[] annotations);

    boolean hasParameters();

    int getParameterCount();
}
