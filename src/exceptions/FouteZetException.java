package exceptions;

public class FouteZetException extends IllegalArgumentException {

    public FouteZetException() {
        this("fouteZet");
    }

    public FouteZetException(String s) {
        super(s);
    }

    public FouteZetException(String message, Throwable cause) {
        super(message, cause);
    }

    public FouteZetException(Throwable cause) {
        super(cause);
    }

}