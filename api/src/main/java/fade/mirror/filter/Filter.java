package fade.mirror.filter;

import fade.mirror.internal.impl.filter.BasicMethodFilter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public abstract class Filter<T>
        implements Predicate<T> {

    static @NotNull MethodFilter forFields() {
        return BasicMethodFilter.create();
    }
}
