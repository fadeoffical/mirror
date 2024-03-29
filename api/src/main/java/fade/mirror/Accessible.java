package fade.mirror;

import fade.mirror.exception.InaccessibleException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Represents an object that can be accessed. This interface is used to provide methods to check the accessibility of an
 * object and to make it accessible.
 *
 * @param <Self> The type of the object.
 * @author fade
 */
public interface Accessible<Self extends Accessible<Self>> {

    /**
     * Checks if the object is package-private.
     *
     * @return {@code true} if the object is package-private, {@code false} otherwise.
     */
    @Contract(pure = true)
    default boolean isPackagePrivate() {
        return !this.isPublic() && !this.isProtected() && !this.isPrivate();
    }

    /**
     * Checks if the object is public.
     *
     * @return {@code true} if the object is public, {@code false} otherwise.
     */
    @Contract(pure = true)
    default boolean isPublic() {
        return Modifier.isPublic(this.getModifiers());
    }

    /**
     * Checks if the object is protected.
     *
     * @return {@code true} if the object is protected, {@code false} otherwise.
     */
    @Contract(pure = true)
    default boolean isProtected() {
        return Modifier.isProtected(this.getModifiers());
    }

    /**
     * Checks if the object is private.
     *
     * @return {@code true} if the object is private, {@code false} otherwise.
     */
    @Contract(pure = true)
    default boolean isPrivate() {
        return Modifier.isPrivate(this.getModifiers());
    }

    @Contract(pure = true)
    int getModifiers();

    /**
     * Checks if the object is static.
     *
     * @return {@code true} if the object is static, {@code false} otherwise.
     */
    @Contract(pure = true)
    default boolean isStatic() {
        return Modifier.isStatic(this.getModifiers());
    }

    /**
     * Checks whether the object is accessible, and if it is not, makes it accessible. If the object cannot be made
     * accessible, an exception is thrown.
     *
     * @return the wrapper.
     */
    @SuppressWarnings("unchecked")
    default @NotNull Self requireAccessible() {
        this.makeAccessible().throwIfInaccessible(() -> InaccessibleException.from("Object is not accessible"));
        return (Self) this;
    }

    @Contract(pure = true)
    default void throwIfInaccessible(@NotNull Supplier<? extends RuntimeException> exception) {
        if (!this.isAccessible()) throw exception.get();
    }

    /**
     * Makes the object accessible. This method is equivalent to calling {@link AccessibleObject#trySetAccessible()} on
     * the wrapped object.
     *
     * @return the wrapper.
     */
    default @NotNull Self makeAccessible() {
        return this.makeAccessible(null);
    }

    /**
     * Checks if the object is accessible. An object is accessible when {@link AccessibleObject#canAccess(Object)}
     * returns {@code true} for the wrapped object.
     *
     * @return {@code true} if the object is accessible, {@code false} otherwise.
     */
    @Contract(pure = true)
    default boolean isAccessible() {
        return this.isAccessible(null);
    }

    @NotNull Self makeAccessible(@Nullable Object instance);

    @Contract(pure = true)
    boolean isAccessible(@Nullable Object instance);

    @SuppressWarnings("unchecked")
    default @NotNull Self requireAccessible(@Nullable Object instance) {
        this.makeAccessible(instance)
                .throwIfInaccessible(instance, () -> InaccessibleException.from("Object is not accessible"));
        return (Self) this;
    }

    @Contract(pure = true)
    default void throwIfInaccessible(@Nullable Object instance, @NotNull Supplier<? extends RuntimeException> exception) {
        if (!this.isAccessible(instance)) throw exception.get();
    }


    @Contract(pure = true)
    @SuppressWarnings("unchecked")
    default @NotNull Self ifAccessible(@NotNull Consumer<Self> consumer) {
        if (this.isAccessible()) consumer.accept((Self) this);
        return (Self) this;
    }

    /**
     * Checks if the object is not accessible, and if it is not, performs the given action on the object.
     *
     * @param consumer the action to perform.
     * @return the wrapper.
     */
    @Contract(pure = true)
    @SuppressWarnings("unchecked")
    default @NotNull Self ifNotAccessible(@NotNull Consumer<Self> consumer) {
        if (!this.isAccessible()) consumer.accept((Self) this);
        return (Self) this;
    }
}
