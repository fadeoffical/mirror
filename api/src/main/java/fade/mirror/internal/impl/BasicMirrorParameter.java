package fade.mirror.internal.impl;

import fade.mirror.MParameter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
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
    @ApiStatus.Internal
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
    @ApiStatus.Internal
    @Contract(value = "_ -> new", pure = true)
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
    public int getAnnotationCount() {
        return this.parameter.getAnnotations().length;
    }
}
