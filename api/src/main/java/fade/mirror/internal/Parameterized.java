package fade.mirror.internal;

import fade.mirror.MParameter;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.stream.Stream;

public interface Parameterized {

    @NotNull Stream<MParameter<?>> getParameters();

    <T> @NotNull Stream<MParameter<T>> getParametersWithType(@NotNull Class<T> type);

    @NotNull Stream<MParameter<?>> getParametersWithAnnotation(@NotNull Class<? extends Annotation> annotation);

    @NotNull Stream<MParameter<?>> getParametersWithAnnotations(@NotNull Class<? extends Annotation>[] annotations);

    @NotNull Stream<MParameter<?>> getParametersWithoutAnnotation(@NotNull Class<? extends Annotation> annotation);

    @NotNull Stream<MParameter<?>> getParametersWithoutAnnotations(@NotNull Class<? extends Annotation>[] annotations);

    boolean areParametersEqual(@NotNull Class<?>[] types);

    boolean isParameterPresent(@NotNull Class<?>[] type);

    boolean isParameterAbsent(@NotNull Class<?>[] type);
}
