package ui;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleUC2 {
    DomeinController domeinController;
    Vertaler vertaler;

    public ConsoleUC2(DomeinController domeinController, Vertaler vertaler) {
        this.domeinController = domeinController;
        this.vertaler = vertaler;
    }

    public void run() {
        String voornaam, familienaam, gebruikersnaam, wachtwoord;
        Scanner input = new Scanner(System.in);
        ResourceBundle bundle = vertaler.getBundle();

        while (true){
            try {
                System.out.printf("%s: ", bundle.getString("voornaam"));
                voornaam = input.nextLine();
                System.out.printf("%s: ", bundle.getString("familienaam"));
                familienaam = input.nextLine();
                System.out.printf("%s: ", bundle.getString("gebruikersnaam"));
                gebruikersnaam = input.nextLine();
                System.out.printf("%s: ", bundle.getString("wachtwoord"));
                wachtwoord = input.nextLine();
                System.out.printf("%s%n", bundle.getString("wachten"));
                domeinController.registreer(voornaam, familienaam, gebruikersnaam, wachtwoord);
                System.out.printf("%s!%n", bundle.getString("succesRegistratie"));
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
