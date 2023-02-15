package fade.mirror.internal.impl;

import fade.mirror.MField;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public final class MFieldImpl<T> implements MField<T> {

    private final Field field;

    private MFieldImpl(@NotNull Field field) {
        this.field = field;
    }

    public static <T> @NotNull MFieldImpl<T> from(@NotNull Field field) {
        return new MFieldImpl<>(field);
    }
}
