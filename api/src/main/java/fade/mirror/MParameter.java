package fade.mirror;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a parameter of a {@link MMethod} or {@link MConstructor}. A parameter has a name and a type. It may also
 * have annotations.
 *
 * @param <Type> the type of the parameter
 * @author fade
 */
public interface MParameter<Type>
        extends Annotated, Named {

    /**
     * Returns the type of this parameter represented by a java class.
     *
     * @return the type.
     */
    @Contract(pure = true)
    @NotNull Class<Type> getType();
}
