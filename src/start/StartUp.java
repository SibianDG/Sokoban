package start;

import domein.DomeinController;
import ui.*;

public class StartUp {
    public static void main(String[] args) {
    	DomeinController domeinController = new DomeinController();
        new Console(domeinController).run();
    }

}
