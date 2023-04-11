package fade.mirror.filter.by;

// see comment from line 7 onwards
@SuppressWarnings({"MarkerInterface", "unused" /* Self */})
public interface ByType<Self extends ByType<Self>> {

    // unfortunately, every class that implements this interface has to declare this method manually
    // because of the generic type parameter. Doing it this way allows the user to specify the type
    // of the filter when calling this method.
    //
    // this is the signature of the method (Replace <Self> with the type of the filter):
    // <ClassType> @NotNull Self<ClassType> ofType(@NotNull Class<ClassType> type);
}
