package fade.mirror;

import fade.mirror.internal.impl.BasicMirrorClass;
import fade.mirror.internal.impl.BasicMirrorConstructor;
import fade.mirror.internal.impl.BasicMirrorField;
import fade.mirror.internal.impl.BasicMirrorMethod;
import org.jetbrains.annotations.Contract;
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
    @Contract(" -> fail")
    private Mirror() {
        throw new UnsupportedOperationException("Instantiating '%s' is forbidden!".formatted(this.getClass()
                .getName()));
    }

    /**
     * Creates a new {@link MClass} instance from the given {@link Class}.
     *
     * @param clazz  the class to create the mirror from.
     * @param <Type> the type of the class.
     * @return the mirror of the given class.
     */
    public static <Type> @NotNull MClass<Type> mirror(@NotNull Class<Type> clazz) {
        return BasicMirrorClass.from(clazz);
    }

    /**
     * Creates a new {@link MConstructor} instance from the given {@link Constructor}.
     *
     * @param constructor the constructor to create the mirror from.
     * @param <Type>      the type of the class.
     * @return the mirror of the given constructor.
     */
    public static <Type> @NotNull MConstructor<Type> mirror(@NotNull Constructor<Type> constructor) {
        return BasicMirrorConstructor.from(constructor);
    }

    /**
     * Creates a new {@link MField} instance from the given {@link Field}.
     *
     * @param field  the field to create the mirror from.
     * @param <Type> the type of the class.
     * @return the mirror of the given field.
     */
    public static <Type> @NotNull MField<Type> mirror(@NotNull Field field) {
        return BasicMirrorField.from(field);
    }

    /**
     * Creates a new {@link MMethod} instance from the given {@link Method}.
     *
     * @param method the method to create the mirror from.
     * @param <Type> the type of the class.
     * @return the mirror of the given method.
     */
    public static <Type> @NotNull MMethod<Type> mirror(@NotNull Method method) {
        return BasicMirrorMethod.from(method);
    }
}
