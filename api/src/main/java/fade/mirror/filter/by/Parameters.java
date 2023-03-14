package fade.mirror.filter.by;

import fade.mirror.filter.RewriteOperation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Parameters<R> {

    @NotNull R withNoParameters();

    @NotNull R withParameters(@NotNull List<Class<?>> parameterTypes, @NotNull RewriteOperation operation);

    @NotNull R withParameters(@NotNull List<Class<?>> parameterTypes);

    @NotNull R withParameter(@NotNull Class<?> parameterType, @NotNull RewriteOperation operation);

    @NotNull R withParameter(@NotNull Class<?> parameterType);

}
