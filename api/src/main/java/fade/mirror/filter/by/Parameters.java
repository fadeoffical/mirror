package fade.mirror.filter.by;

import fade.mirror.filter.RewriteOperation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Parameters<R> {

    @NotNull R withNoParameters();

    @NotNull R withParameters(@NotNull List<Class<?>> parameterTypes, @NotNull RewriteOperation operation);

    @NotNull
    default R withParameters(@NotNull List<Class<?>> parameterTypes) {
        return this.withParameters(parameterTypes, RewriteOperation.Append);
    }

    @NotNull
    default R withParameter(@NotNull Class<?> parameterType, @NotNull RewriteOperation operation) {
        return this.withParameters(List.of(parameterType), operation);
    }

    @NotNull
    default R withParameter(@NotNull Class<?> parameterType) {
        return this.withParameter(parameterType, RewriteOperation.Append);
    }
}
