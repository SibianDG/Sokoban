package exceptions;

public class FouteAanmeldOfRegistreerGegevensException extends IllegalArgumentException {

    /*
    public FouteAanmeldOfRegistreerGegevensException() {
        this("U heeft een verkeerde gezet aangegeven, probeer opnieuw!");
    }
     */

    public FouteAanmeldOfRegistreerGegevensException(String s) {
        super(s);
    }

    /*
    public FouteAanmeldOfRegistreerGegevensException(String message, Throwable cause) {
        super(message, cause);
    }

    public FouteAanmeldOfRegistreerGegevensException(Throwable cause) {
        super(cause);
    }

     */

}