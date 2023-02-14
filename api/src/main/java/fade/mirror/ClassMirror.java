package mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.Optional;

public final class ClassMirror {

    private final @NotNull Class<?> clazz;

    private ClassMirror(@NotNull Class<?> clazz) {
        this.clazz = clazz;
    }

    static ClassMirror fromClass(@NotNull Class<?> clazz) {
        return new ClassMirror(clazz);
    }

    public @NotNull Optional<Constructor<?>> getConstructor(@NotNull ConstructorAccessor accessor) {

    }
}
