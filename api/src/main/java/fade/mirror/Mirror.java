package fade.mirror;

import fade.mirror.internal.impl.MClassImpl;
import fade.mirror.internal.impl.MConstructorImpl;
import fade.mirror.internal.impl.MFieldImpl;
import fade.mirror.internal.impl.MMethodImpl;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The class that provides the entry point to the API.
 *
 * @author fade
 */
public final class Mirror {

    /**
     * The private constructor that prevents the instantiation of the class. Calling this constructor will throw an
     * {@link UnsupportedOperationException}.
     */
    private Mirror() {
        throw new UnsupportedOperationException("Instantiating '%s' is forbidden!".formatted(Mirror.class.getName()));
    }

    /**
     * Creates a new {@link MClass} instance from the given {@link Class}.
     *
     * @param clazz the class to create the mirror from.
     * @param <T>   the type of the class.
     * @return the mirror of the given class.
     */
    public static <T> @NotNull MClass<T> mirror(@NotNull Class<T> clazz) {
        return MClassImpl.from(clazz);
    }

    /**
     * Creates a new {@link MConstructor} instance from the given {@link Constructor}.
     *
     * @param constructor the constructor to create the mirror from.
     * @param <T>         the type of the class.
     * @return the mirror of the given constructor.
     */
    public static <T> @NotNull MConstructor<T> mirror(@NotNull Constructor<T> constructor) {
        return MConstructorImpl.from(constructor);
    }

    /**
     * Creates a new {@link MField} instance from the given {@link Field}.
     *
     * @param field the field to create the mirror from.
     * @param <T>   the type of the class.
     * @return the mirror of the given field.
     */
    public static <T> @NotNull MField<T> mirror(@NotNull Field field) {
        return MFieldImpl.from(field);
    }

    /**
     * Creates a new {@link MMethod} instance from the given {@link Method}.
     *
     * @param method the method to create the mirror from.
     * @param <T>    the type of the class.
     * @return the mirror of the given method.
     */
    public static <T> @NotNull MMethod<T> mirror(@NotNull Method method) {
        return MMethodImpl.from(method);
    }
}
