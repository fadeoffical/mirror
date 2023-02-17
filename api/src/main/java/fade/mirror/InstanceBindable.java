package fade.mirror;

import org.jetbrains.annotations.NotNull;

public interface InstanceBindable {

    /**
     * Binds this method to the given object.
     * <p>
     * A non-static method can only be operated on in any meaningful way if and only if an object exists which to
     * reference. This method binds the method to the given object, which will be used as the reference.
     * </p>
     * <p>
     * If the method is already bound to an object, this method will unbind it from the old object and bind it to the
     * new one. If the method is static, this method have no effect.
     * </p>
     *
     * @param object The object to bind this method to.
     */
    void bindToObject(@NotNull Object object);
}
