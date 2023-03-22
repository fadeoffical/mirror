package fade.mirror;

import fade.mirror.internal.impl.BasicMirrorException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public sealed interface MException<T extends Throwable>
        permits BasicMirrorException {

    @Contract(pure = true)
    @NotNull Class<T> getType();

    /**
     * Throws the exception represented by this object. <br/> Java does not allow calling a method by a reserved
     * keyword, so this method is named {@code yeet}.
     */
    void yeet() throws T;
}
