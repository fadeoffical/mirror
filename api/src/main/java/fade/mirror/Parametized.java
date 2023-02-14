package fade.mirror;

import org.jetbrains.annotations.NotNull;

public interface Parametized {

    boolean areParametersEqual(@NotNull Class<?>[] types);
}
