package mirror;

public final class ClassMirror {

    private Class<?> clazz;

    private ClassMirror(Class<?> clazz) {
        this.clazz = clazz;
    }

    static ClassMirror fromClass(Class<?> clazz) {
        return new ClassMirror(clazz);
    }
}
