package fade.mirror;

import fade.mirror.internal.Accessible;
import fade.mirror.internal.Annotated;
import fade.mirror.internal.Invokable;
import fade.mirror.internal.Parameterized;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public interface MConstructor<T> extends Accessible<MConstructor<T>>, Parameterized, Annotated, Invokable<T> {

    @NotNull Constructor<T> getRawConstructor();

    @NotNull MClass<T> getDeclaringClass();


}
