package fade.mirror;

import fade.mirror.internal.impl.BasicMirrorMethod;

/**
 * Represents a method of a {@link MClass}. A method has a name, a return type, a list of parameters and a list of
 * exceptions. It may also have annotations.
 *
 * @param <Type> the type of the method.
 * @author fade
 */
public sealed interface MMethod<Type>
        extends Invokable<Type>, Accessible<MMethod<Type>>, Copyable<MMethod<Type>>, Annotated, Named, Declared
        permits BasicMirrorMethod {

    // todo: return type
    // todo: exceptions

}
