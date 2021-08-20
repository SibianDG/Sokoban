package ui;

import domein.DomeinController;
import exceptions.FouteAanmeldOfRegistreerGegevensException;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleUC1 {

    private DomeinController domeinController;
    private Vertaler vertaler;

    public ConsoleUC1(DomeinController domeinController, Vertaler vertaler) {
        this.domeinController = domeinController;
        this.vertaler = vertaler;
    }

    public void run() {
        String gebruikersnaam, wachtwoord;
        Scanner input = new Scanner(System.in);
        ResourceBundle bundle = vertaler.getBundle();

        while (true){
            try {
                System.out.printf("%s: ", bundle.getString("gebruikersnaam"));
                gebruikersnaam = input.nextLine();
                System.out.printf("%s: ", bundle.getString("wachtwoord"));
                wachtwoord = input.nextLine();
                System.out.printf("%s%n", bundle.getString("wachten"));
                if (gebruikersnaam.isEmpty() || wachtwoord.isEmpty()){
                    throw new FouteAanmeldOfRegistreerGegevensException("LoginEmpty");
                }
                domeinController.meldAan(gebruikersnaam, wachtwoord);
                break;
            } catch (InputMismatchException ime){
                input.nextLine();
                System.out.printf("%s!%n%n", bundle.getString("keuzegetal"));
            } catch (NumberFormatException nfe){
                System.out.println(bundle.getString(nfe.getMessage()));
            } catch (IllegalArgumentException iae){
                System.out.println(bundle.getString(iae.getMessage()));
            }
        }
    }
}
