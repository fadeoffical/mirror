package fade.mirror;

import fade.mirror.exception.InvocationException;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class ConstructorAccessor<T> implements Accessible<Constructor<T>>, Parametized, Annotated {

    private final Constructor<T> constructor;

    private ConstructorAccessor(@NotNull Constructor<T> constructor) {
        this.constructor = constructor;
    }

    static <T> ConstructorAccessor<T> from(@NotNull Constructor<T> constructor) {
        return new ConstructorAccessor<>(constructor);
    }

    @Override
    public boolean isAccessible() {
        return this.constructor.canAccess(null);
    }

    @Override
    public @NotNull ConstructorAccessor<T> makeAccessible() {
        if (!this.isAccessible()) {
            this.constructor.trySetAccessible();
        }
        return this;
    }

    @Override
    public @NotNull ConstructorAccessor<T> requireAccessible() {
        return (ConstructorAccessor<T>) Accessible.super.requireAccessible();
    }

    @Override
    public @NotNull ConstructorAccessor<T> requireAccessible(@NotNull Supplier<? extends RuntimeException> exception) {
        this.makeAccessible();
        if (!this.isAccessible()) throw exception.get();

        return this;
    }

    @Override
    public @NotNull ConstructorAccessor<T> ifAccessible(@NotNull Consumer<Constructor<T>> consumer) {
        if (this.isAccessible()) consumer.accept(this.constructor);
        return this;
    }

    @Override
    public @NotNull ConstructorAccessor<T> ifNotAccessible(@NotNull Consumer<Constructor<T>> consumer) {
        if (!this.isAccessible()) consumer.accept(this.constructor);
        return this;
    }

    public T invoke(@NotNull Object... arguments) {
        try {
            return this.constructor.newInstance(arguments);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException exception) {
            throw InvocationException.from("Could not invoke constructor '%s' from '%s'".formatted(this.getPrettyConstructorRepresentation(), this.constructor.getDeclaringClass()
                    .getName()), exception);
        }
    }

    private @NotNull String getPrettyConstructorRepresentation() {
        StringBuilder builder = new StringBuilder().append(this.constructor.getName()).append('(');
        for (Class<?> type : this.constructor.getParameterTypes()) builder.append(type.getSimpleName()).append(", ");
        return builder.append(")").toString();
    }

    @Override
    public boolean areParametersEqual(@NotNull Class<?>[] types) {
        return Arrays.equals(this.constructor.getParameterTypes(), types);
    }

    @Override
    public boolean areAnnotationsEqual(@NotNull Annotation[] annotations) {
        return Arrays.equals(this.constructor.getDeclaredAnnotations(), annotations);
    }
}
