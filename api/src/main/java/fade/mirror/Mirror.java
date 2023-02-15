package fade.mirror;

import fade.mirror.internal.impl.MClassImpl;
import fade.mirror.internal.impl.MConstructorImpl;
import fade.mirror.internal.impl.MFieldImpl;
import fade.mirror.internal.impl.MMethodImpl;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class Mirror {

    private Mirror() {
        throw new UnsupportedOperationException("Instantiating '%s' is forbidden!".formatted(Mirror.class.getName()));
    }

    public static <T> @NotNull MClass<T> mirror(@NotNull Class<T> clazz) {
        return MClassImpl.from(clazz);
    }

    public static <T> @NotNull MConstructor<T> mirror(@NotNull Constructor<T> constructor) {
        return MConstructorImpl.from(constructor);
    }

    public static <T> @NotNull MField<T> mirror(@NotNull Field field) {
        return MFieldImpl.from(field);
    }

    public static <T> @NotNull MMethod<T> mirror(@NotNull Method method) {
        return MMethodImpl.from(method);
    }
}
