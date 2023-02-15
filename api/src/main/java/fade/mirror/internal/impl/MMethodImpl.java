package fade.mirror.internal.impl;

import fade.mirror.MMethod;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

public final class MMethodImpl<T> implements MMethod<T> {

    private final Method method;

    private MMethodImpl(@NotNull Method method) {
        this.method = method;
    }

    public static <T> MMethodImpl<T> from(@NotNull Method method) {
        return new MMethodImpl<>(method);
    }
}
