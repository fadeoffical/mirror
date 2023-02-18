package fade.mirror;

import fade.mirror.internal.impl.MConstructorImpl;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

/**
 * Represents a constructor of a {@link MClass}. A constructor has parameters and may also have annotations.
 *
 * @param <T> the type of the class that declares this constructor
 * @author fade
 */
public sealed interface MConstructor<T>
        extends Invokable<T>, Accessible<MConstructor<T>>, Parameterized, Annotated, Declared
        permits MConstructorImpl {

    /**
     * Returns the raw constructor. This is the constructor that is represented by this object.
     *
     * @return the raw constructor.
     */
    @NotNull Constructor<T> getRawConstructor();

}
