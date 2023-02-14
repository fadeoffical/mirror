package fade.mirror;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

public interface Annotated {

    boolean areAnnotationsEqual(@NotNull Annotation[] annotations);
}
