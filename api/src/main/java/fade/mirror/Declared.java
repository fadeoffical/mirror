package fade.mirror;

import org.jetbrains.annotations.NotNull;

public interface Declared {

    @NotNull MClass<?> getDeclaringClass();
}
