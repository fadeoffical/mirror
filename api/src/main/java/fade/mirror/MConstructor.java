package fade.mirror;

import fade.mirror.internal.impl.BasicMirrorConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

/**
 * Represents a constructor of a {@link MClass}. A constructor has parameters and may also have annotations.
 *
 * @param <Type> the type of the class that declares this constructor
 * @author fade
 */
public sealed interface MConstructor<Type>
        extends Invokable<Type>, Accessible<MConstructor<Type>>, Annotated, Declared
        permits BasicMirrorConstructor {

    /**
     * Returns the raw constructor. This is the constructor that is represented by this object.
     *
     * @return the raw constructor.
     */
    @Contract(pure = true)
    @NotNull Constructor<Type> getRawConstructor();

}
