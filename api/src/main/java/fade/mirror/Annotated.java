package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Represents an element that can be annotated.
 *
 * @author fade
 */
public interface Annotated {

    /**
     * Returns a stream of all annotations of this element. The stream is ordered by the declaration order of the
     * annotations in the source code. The stream may be empty if the element has no annotations. The stream will never
     * be {@code null}.
     *
     * @return all annotations of this element
     */
    @NotNull Stream<Annotation> getAnnotations();

    /**
     * Returns a stream of all annotations of this element that match the given filter. The stream is ordered by the
     * declaration order of the annotations in the source code. The stream may be empty if the element has no
     * annotations that match the filter. The stream will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return all annotations of this element that match the filter.
     */
    @NotNull Stream<Annotation> getAnnotations(@NotNull Predicate<Annotation> filter);

    /**
     * Returns an optional containing the first annotation of this element that matches the given filter. The optional
     * may be empty if the element has no annotations that match the filter. The optional will never be {@code null}.
     *
     * @param filter the filter to apply.
     * @return the first annotation of this element that matches the filter.
     */
    @NotNull Optional<Annotation> getAnnotation(@NotNull Predicate<Annotation> filter);

    /**
     * Checks if this element is annotated with all the given annotations. This method is equivalent to calling
     * {@link #isAnnotatedWith(Class)} for each annotation and checking if all calls return {@code true}. If the given
     * array is empty, this method returns {@code true}. If the given array contains an annotation that is not present
     * on this element, this method will return {@code false}.
     *
     * @param annotations the annotations to check for.
     * @return {@code true} if this element is annotated with all the given annotations, {@code false} otherwise.
     */
    boolean isAnnotatedWith(@NotNull Class<? extends Annotation>[] annotations);

    /**
     * Checks if this element is annotated with the given annotation. If the given annotation is not present on this
     * element, this method will return {@code false}.
     *
     * @param annotation the annotation to check for.
     * @return {@code true} if this element is annotated with the given annotation, {@code false} otherwise.
     */
    boolean isAnnotatedWith(@NotNull Class<? extends Annotation> annotation);

    /**
     * Returns an optional containing the first annotation of this element that matches the given type. The optional may
     * be empty if the element has no annotation that match the type. The optional will never be {@code null}.
     *
     * @param type the annotation type to match.
     * @param <T>  the type of the annotation.
     * @return the first annotation of this element that matches the type.
     */
    <T extends Annotation> @NotNull Optional<T> getAnnotationOfType(@NotNull Class<T> type);

    /**
     * Returns whether this element has any annotations.
     *
     * @return {@code true} if this element has any annotations, {@code false} otherwise.
     */
    boolean isAnnotated();

    /**
     * Returns the number of annotations of this element.
     *
     * @return the number of annotations of this element.
     */
    int getAnnotationCount();
}
