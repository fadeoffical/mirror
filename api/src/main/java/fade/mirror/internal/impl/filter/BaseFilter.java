package fade.mirror.internal.impl.filter;

import fade.mirror.filter.Filter;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public abstract class BaseFilter<Type> implements Filter<Type> {

    private final Set<Predicate<Type>> criteria;

    BaseFilter() {
        this.criteria = new HashSet<>(1); // expecting the user to filter for only one criterion is reasonable
    }

    protected @NotNull Set<Predicate<Type>> getCriteria() {
        return Collections.unmodifiableSet(this.criteria);
    }

    public final @NotNull BaseFilter<Type> addCriterion(@NotNull Predicate<Type> criterion) {
        this.criteria.add(criterion);
        return this;
    }

    @Override
    public boolean test(@NotNull Type type) {
        return this.criteria.stream().allMatch(filter -> filter.test(type));
    }
}
