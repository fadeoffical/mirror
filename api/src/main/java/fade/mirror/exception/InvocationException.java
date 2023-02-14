package fade.mirror.exception;

public final class InvocationException extends MirrorException {

    private InvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static InvocationException from(String message, Throwable cause) {
        return new InvocationException(message, cause);
    }
}
