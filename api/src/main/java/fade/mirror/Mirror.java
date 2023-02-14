package fade.mirror;

import org.jetbrains.annotations.NotNull;

public final class Mirror {

    private Mirror() {
        throw new UnsupportedOperationException("Instantiating '%s' is forbidden!".formatted(Mirror.class.getName()));
    }

    public static <T> @NotNull ClassMirror<T> mirror(@NotNull Class<T> clazz) {
        return ClassMirror.fromClass(clazz);
    }
//
//    public static @NotNull FieldMirror mirror(@NotNull Field field) {
//    }
//
//    public static <T> @NotNull ConstructorMirror<T> mirror(@NotNull Constructor<T> constructor) {
//    }
}
