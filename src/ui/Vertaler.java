package ui;

import java.util.Locale;
import java.util.ResourceBundle;

public class Vertaler {

    private Locale taal;
    private ResourceBundle bundle;

    public Vertaler(String taal){
        bundle = ResourceBundle.getBundle("i18n", Locale.forLanguageTag(taal));
    }

    public ResourceBundle getBundle(){
        return bundle;
    }

}
