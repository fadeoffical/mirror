package fade.mirror;

import fade.mirror.internal.impl.filter.FieldFilter;

import java.util.function.Predicate;

public abstract class Filter<T>
        implements Predicate<T> {

    public static FieldFilter forFields() {
        return FieldFilter.create();
    }
}
