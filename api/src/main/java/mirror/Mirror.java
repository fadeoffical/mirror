package mirror;

import org.jetbrains.annotations.NotNull;

public class Mirror {

    public static @NotNull ClassMirror mirror(@NotNull Class<?> clazz) {
        return ClassMirror.fromClass(clazz);
    }
}
