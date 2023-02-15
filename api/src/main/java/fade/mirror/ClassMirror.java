package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public final class ClassMirror<T> {

    private final @NotNull Class<T> clazz;

    private ClassMirror(@NotNull Class<T> clazz) {
        this.clazz = clazz;
    }

    static <T> ClassMirror<T> fromClass(@NotNull Class<T> clazz) {
        return new ClassMirror<>(clazz);
    }

    public @NotNull Optional<MConstructor<T>> getConstructor(@NotNull ConstructorFilter predicate) {
        return this.getConstructors().filter(predicate).findFirst();
    }

    public @NotNull Stream<MConstructor<T>> getConstructors() {
        return this.constructorStream().map(MConstructor::new);
    }

    @SuppressWarnings("unchecked") // cast should always succeed
    private Stream<Constructor<T>> constructorStream() {
        return Arrays.stream(this.clazz.getConstructors()).map(constructor -> (Constructor<T>) constructor);
    }
}
