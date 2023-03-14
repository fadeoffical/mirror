package fade.mirror.filter.by;

// see comment from line 7 onwards
@SuppressWarnings({"MarkerInterface", "unused" /* R */})
public interface Type<R> {

    // unfortunately, every class that implements this interface has to declare this method manually
    // because of the generic type parameter. Doing it this way allows the user to specify the type
    // of the filter when calling this method.
    //
    // this is the signature of the method (Replace <R> with the type of the filter):
    // <C> @NotNull R<C> ofType(@NotNull Class<C> type);
}
