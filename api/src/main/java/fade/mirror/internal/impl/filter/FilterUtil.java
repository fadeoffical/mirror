package fade.mirror.internal.impl.filter;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

/**
 * A utility class for filters.
 *
 * @author fade
 */
@ApiStatus.Internal
final class FilterUtil {

    /**
     * A private constructor to prevent instantiation.
     */
    private FilterUtil() {
        throw new UnsupportedOperationException("Instantiating '%s' is forbidden!".formatted(this.getClass()
                .getName()));
    }

    /**
     * Checks if the given parameter type is one of the required parameter types.
     *
     * @param parameterTypes the required parameter types
     * @param parameterType  the parameter type to check
     * @return {@code true} if the given parameter type is one of the required parameter types, {@code false} otherwise
     */
    static boolean isParameterOneOfRequired(Class<?> @NotNull [] parameterTypes, @NotNull Class<?> parameterType) {
        for (Class<?> filterParameterType : parameterTypes) {
            if (filterParameterType.isAssignableFrom(parameterType)) return true;
        }
        return false;
    }

    /**
     * Checks if the given annotation is one of the required annotations.
     *
     * @param annotations the required annotations
     * @param annotation  the annotation to check
     * @return {@code true} if the given annotation is one of the required annotations, {@code false} otherwise
     */
    static boolean isAnnotationOneOfRequired(Class<? extends Annotation> @NotNull [] annotations, @NotNull Annotation annotation) {
        for (Class<? extends Annotation> annotationType : annotations) {
            if (annotationType.isAssignableFrom(annotation.annotationType())) return true;
        }
        return false;
    }
}
