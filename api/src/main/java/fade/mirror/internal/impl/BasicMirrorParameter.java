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

/**
 * Basic implementation of {@link MParameter}.
 *
 * @param <T> The type of the parameter.
 */
public final class BasicMirrorParameter<T>
        implements MParameter<T> {

    private final Parameter parameter;

    /**
     * Creates a new {@link BasicMirrorParameter} from the given {@link Parameter}.
     *
     * @param parameter The parameter to create the mirror parameter from.
     */
    private BasicMirrorParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    /**
     * Creates a new {@link BasicMirrorParameter} from the given {@link Parameter}. This method should not be used
     * directly.
     *
     * @param parameter The parameter to create the mirror parameter from.
     * @return The created mirror parameter.
     */
    public static @NotNull BasicMirrorParameter<?> from(@NotNull Parameter parameter) {
        return new BasicMirrorParameter<>(parameter);
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Class<T> getType() {
        return (Class<T>) this.parameter.getType();
    }

    @Override
    public @NotNull String getName() {
        return this.parameter.getName();
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
    public boolean isAnnotatedWith(@NotNull Class<? extends Annotation>[] annotations) {
        Set<Class<? extends Annotation>> annotationList = Set.of(annotations);
        return this.getAnnotations().map(Annotation::annotationType).anyMatch(annotationList::contains);
    }

    @Override
    public boolean isAnnotatedWith(@NotNull Class<? extends Annotation> annotation) {
        return this.getAnnotations().map(Annotation::annotationType).anyMatch(clazz -> clazz == annotation);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C extends Annotation> @NotNull Optional<C> getAnnotationOfType(@NotNull Class<C> type) {
        return (Optional<C>) this.getAnnotation(annotation -> annotation.annotationType() == type);
    }

    @Override
    public boolean isAnnotated() {
        return this.getAnnotationCount() > 0;
    }

    @Override
    public int getAnnotationCount() {
        return this.parameter.getAnnotations().length;
    }
}
