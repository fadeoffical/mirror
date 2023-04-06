package fade.mirror.filter.by;

import fade.mirror.filter.RewriteOperation;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.List;

public interface Annotations<R> {

    @NotNull R withNoAnnotations();

    @NotNull R withAnnotations(@NotNull List<Class<? extends Annotation>> annotations, @NotNull RewriteOperation operation);

    default @NotNull R withAnnotations(@NotNull List<Class<? extends Annotation>> annotations) {
        return this.withAnnotations(annotations, RewriteOperation.Append);
    }

    default @NotNull R withAnnotation(@NotNull Class<? extends Annotation> annotation, @NotNull RewriteOperation operation) {
        return this.withAnnotations(List.of(annotation), operation);
    }

    default @NotNull R withAnnotation(@NotNull Class<? extends Annotation> annotation) {
        return this.withAnnotation(annotation, RewriteOperation.Append);
    }
}
