package fade.mirror;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a class member that can be declared in a class.
 *
 * @author fade
 */
public interface Declared {

    /**
     * Returns the class that declares this member.
     *
     * @return the declaring class.
     */
    @Contract(pure = true)
    @NotNull MClass<?> getDeclaringClass();
}
