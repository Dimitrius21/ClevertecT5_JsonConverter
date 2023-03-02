package bzh.json.parser.exception;

public class ObjectManipulateException extends RuntimeException {
    public ObjectManipulateException() {
    }

    public ObjectManipulateException(String message) {
        super(message);
    }

    public ObjectManipulateException(String message, Throwable cause) {
        super(message, cause);
    }
}
