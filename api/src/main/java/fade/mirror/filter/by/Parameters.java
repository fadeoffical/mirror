package fade.mirror.filter.by;

import fade.mirror.filter.RewriteOperation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Parameters<Self extends Parameters<Self>> {

    @NotNull Self withNoParameters();

    @NotNull Self withParameters(@NotNull List<Class<?>> parameterTypes, @NotNull RewriteOperation operation);

    default @NotNull Self withParameters(@NotNull List<Class<?>> parameterTypes) {
        return this.withParameters(parameterTypes, RewriteOperation.Append);
    }

    default @NotNull Self withParameter(@NotNull Class<?> parameterType, @NotNull RewriteOperation operation) {
        return this.withParameters(List.of(parameterType), operation);
    }

    default @NotNull Self withParameter(@NotNull Class<?> parameterType) {
        return this.withParameter(parameterType, RewriteOperation.Append);
    }
}
