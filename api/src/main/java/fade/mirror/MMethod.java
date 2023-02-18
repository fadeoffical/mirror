package fade.mirror;

import fade.mirror.internal.impl.MMethodImpl;

/**
 * Represents a method of a {@link MClass}. A method has a name, a return type, a list of parameters and a list of exceptions.
 * It may also have annotations.
 *
 * @param <T> the type of the method.
 * @author fade
 */
public sealed interface MMethod<T>
        extends Invokable<T>,
                Accessible<MMethod<T>>,
                Copyable<MMethod<T>>,
                Parameterized,
                Annotated,
                Named,
                Declared,
                InstanceBindable
        permits MMethodImpl {

    // todo: return type
    // todo: exceptions

}
