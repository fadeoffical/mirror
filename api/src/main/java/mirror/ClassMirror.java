package mirror;

import org.jetbrains.annotations.NotNull;

public final class ClassMirror {

    private final Class<?> clazz;

    private ClassMirror(@NotNull Class<?> clazz) {
        this.clazz = clazz;
    }

    static ClassMirror fromClass(@NotNull Class<?> clazz) {
        return new ClassMirror(clazz);
    }
}
