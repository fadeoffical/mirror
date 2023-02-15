package fade.mirror;

import fade.mirror.internal.Annotated;
import org.jetbrains.annotations.NotNull;

public interface MParameter<T> extends Annotated {

    @NotNull Class<T> getType();
}
