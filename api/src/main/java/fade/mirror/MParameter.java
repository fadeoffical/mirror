package fade.mirror;

import fade.mirror.internal.Annotated;
import fade.mirror.internal.Native;
import fade.mirror.internal.Typed;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class MParameter<T> implements Native<Parameter>, Typed<T>, Annotated {

    private final Parameter parameter;

    MParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean areAnnotationsPresent(@NotNull Annotation[] annotations) {
        return false;
    }

    @Override
    public boolean isAnnotationPresent(@NotNull Annotation annotation) {
        return false;
    }

    @Override
    public @NotNull Parameter getNativeReflectionObject() {
        return this.parameter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Class<T> getType() {
        return (Class<T>) this.parameter.getType();
    }
}
