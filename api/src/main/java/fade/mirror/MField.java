package fade.mirror;

/**
 * Represents a field inside a {@link MClass}. A field has a name, a type and a value. It may also have annotations.
 *
 * @param <T> the type of the class.
 * @author fade <fadeoffical@gmail.com>
 */

public interface MField<T>
        extends Accessible<MField<T>>, Annotated, Named, Declared, InstanceBindable {
}
