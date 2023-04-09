package fade.mirror.filter;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum TypeComparisonOperation {

    Equality,
    Assignability;

    @Contract(pure = true)
    public boolean compare(@NotNull Class<?> object, @NotNull Class<?> subject) {
        return switch (this) {
            case Equality -> object.equals(subject);
            case Assignability -> object.isAssignableFrom(subject);
        };
    }
}
