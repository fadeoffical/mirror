package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public interface MConstructor<T> extends Accessible<MConstructor<T>>, Parameterized, Annotated, Invokable<T> {

    @NotNull Constructor<T> getRawConstructor();

    @NotNull MClass<T> getDeclaringClass();


}
