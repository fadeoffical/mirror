package fade.mirror.filter;

import fade.mirror.internal.impl.filter.BasicConstructorFilter;
import fade.mirror.internal.impl.filter.BasicMethodFilter;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public abstract class Filter<T>
        implements Predicate<T> {

    static @NotNull MethodFilter forMethods() {
        return BasicMethodFilter.create();
    }

    static @NotNull ConstructorFilter forConstructors() {
        return BasicConstructorFilter.create();
    }

    // TODO: Add filters for fields, classes and annotations
    //
    //    static @NotNull FieldFilter forFields() {
    //        return BasicMethodFilter.create();
    //    }
    //
    //    static @NotNull ClassFilter forClasses() {
    //        return BasicMethodFilter.create();
    //    }
    //
    //    static @NotNull AnnotationFilter forAnnotations() {
    //        return BasicMethodFilter.create();
    //    }
}
