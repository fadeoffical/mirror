package fade.mirror;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a method or constructor that can be invoked.
 *
 * @param <T> the return type of the method or constructor
 * @author fade
 */
public interface Invokable<T> {

    @Nullable T invokeWithInstance(@Nullable Object instance, @Nullable Object... arguments);

    /**
     * Invokes the method or constructor represented by this object.
     *
     * @param arguments the arguments to the method or constructor.
     * @return the result of the method or constructor.
     */
    default @Nullable T invokeWithoutInstance(@Nullable Object... arguments) {
        return this.invokeWithInstance(null, arguments);
    }

    /**
     * Tests whether the method or constructor represented by this object can be invoked with the given arguments.
     *
     * @param arguments the arguments to test.
     * @return whether the method or constructor can be invoked with the given arguments.
     */
    boolean invokableWith(@Nullable Object... arguments);

    /**
     * Gets the return type of the method or constructor represented by this object.
     *
     * @return the return type.
     */
    @NotNull Class<T> getReturnType();
}
