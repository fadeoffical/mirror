package fade.mirror;

import org.jetbrains.annotations.NotNull;

public interface MParameter<T> extends Annotated, Named {

    @NotNull Class<T> getType();
}
