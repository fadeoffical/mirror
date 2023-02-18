package fade.mirror;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a field inside a {@link MClass}. A field has a name, a type and a value. It may also have annotations.
 *
 * @param <T> the type of the class.
 * @author fade
 */

public interface MField<T>
        extends Accessible<MField<T>>, Annotated, Named, Declared, InstanceBindable {

    @NotNull Class<T> getType();
}
