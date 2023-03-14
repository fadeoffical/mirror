package fade.mirror.filter.by;

import fade.mirror.filter.RewriteOperation;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.List;

public interface Annotations<R> {

    @NotNull R withNoAnnotations();

    @NotNull R withAnnotations(@NotNull List<Class<? extends Annotation>> annotations, @NotNull RewriteOperation operation);

    @NotNull R withAnnotations(@NotNull List<Class<? extends Annotation>> annotations);

    @NotNull R withAnnotation(@NotNull Class<? extends Annotation> annotation, @NotNull RewriteOperation operation);

    @NotNull R withAnnotation(@NotNull Class<? extends Annotation> annotation);

}
