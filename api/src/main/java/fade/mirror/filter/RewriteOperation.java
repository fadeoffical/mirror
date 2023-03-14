package fade.mirror.filter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public enum RewriteOperation {
    Append,
    Prepend,
    Replace;

    public <T> void apply(@NotNull List<T> list, @NotNull List<T> other) {
        switch (this) {
            case Append -> list.addAll(other);
            case Prepend -> list.addAll(0, other);
            case Replace -> {
                list.clear();
                list.addAll(other);
            }
        }
    }
}
