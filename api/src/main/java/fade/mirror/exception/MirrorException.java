package fade.mirror.exception;

public class MirrorException extends RuntimeException {

    MirrorException() {
    }

    MirrorException(String message) {
        super(message);
    }

    MirrorException(String message, Throwable cause) {
        super(message, cause);
    }

    MirrorException(Throwable cause) {
        super(cause);
    }

    MirrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
