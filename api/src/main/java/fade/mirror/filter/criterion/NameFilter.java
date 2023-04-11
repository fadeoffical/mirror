package fade.mirror.filter.criterion;

import org.jetbrains.annotations.NotNull;

public interface NameFilter<Self> {

    /**
     * Adds a required name to this filter. The field filter will only keep fields with the specified name.
     * <p>
     * If the name is already set, the new name will replace the old one.
     * <br>
     * If the name is empty, the filter will not keep any fields.
     * <br>
     * If the name is not empty, the filter will only keep fields with the specified name.
     * <br>
     * </p>
     *
     * @param name the name of the field
     * @return this filter
     */
    @NotNull Self withName(@NotNull String name);
}
