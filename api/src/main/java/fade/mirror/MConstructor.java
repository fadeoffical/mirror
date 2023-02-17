package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public non-sealed interface MConstructor<T> extends Invokable<T>, Accessible<MConstructor<T>>, Parameterized, Annotated, Declared {

    @NotNull Constructor<T> getRawConstructor();

}
