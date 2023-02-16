package fade.mirror.internal.impl;

import fade.mirror.MParameter;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class MParameterImpl<T> implements MParameter<T> {

    private final Parameter parameter;

    private MParameterImpl(Parameter parameter) {
        this.parameter = parameter;
    }

    public static @NotNull MParameterImpl<?> from(@NotNull Parameter parameter) {
        return new MParameterImpl<>(parameter);
    }

    @Override
    public @NotNull Stream<Annotation> getAnnotations() {
        return Arrays.stream(this.parameter.getAnnotations());
    }

    @Override
    public @NotNull Stream<Annotation> getAnnotations(@NotNull Predicate<Annotation> filter) {
        return this.getAnnotations().filter(filter);
    }

    @Override
    public @NotNull Optional<Annotation> getAnnotation(@NotNull Predicate<Annotation> filter) {
        return this.getAnnotations(filter).findFirst();
    }

    @Override
    @SafeVarargs
    public final boolean isAnnotatedWith(@NotNull Class<? extends Annotation>... annotations) {
        Set<Class<? extends Annotation>> annotationList = Set.of(annotations);
        return this.getAnnotations().map(Annotation::annotationType).anyMatch(annotationList::contains);
    }

    @Override
    public @NotNull Optional<Annotation> getAnnotationOfType(@NotNull Class<? extends Annotation> type) {
        return this.getAnnotation(annotation -> annotation.annotationType() == type);
    }

    @Override
    public boolean isAnnotated() {
        return this.getAnnotationCount() > 0;
    }

    @Override
    public int getAnnotationCount() {
        return this.parameter.getAnnotations().length;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Class<T> getType() {
        return (Class<T>) this.parameter.getType();
    }
}
