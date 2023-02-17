package fade.mirror;

import org.jetbrains.annotations.NotNull;

public interface Named {

    @NotNull String getName();

    boolean isNameEqualTo(@NotNull String name);
}
