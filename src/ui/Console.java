package ui;

import domein.DomeinController;
import exceptions.FouteAanmeldOfRegistreerGegevensException;

import java.util.*;

public class Console {

    private DomeinController domeinController;
    private Vertaler vertaler;
    private ResourceBundle bundel;

    public Console(DomeinController domeinController){
        this.domeinController = domeinController;
    }

    public void run() {

        Scanner input = new Scanner(System.in);
        boolean herhalen = true;
        int keuze = -1;

        while (herhalen){
            try {
                System.out.print("Welke taal (en, fr, nl): ");
                String taal = input.next().toLowerCase();
                if (taal.equals("en") || taal.equals("fr") || taal.equals("nl")) {
                    this.vertaler = new Vertaler(taal);
                    this.bundel = vertaler.getBundle();                  
                    herhalen = false;
                } else {
                    System.out.println("Kies een geldige taal.");
                }
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            }
        }

        herhalen = true;
        while (herhalen) {

            try {
                while (keuze >= 3 || keuze < 0) {
                    System.out.printf("%s:%n0. %s%n1. %s%n2. %s%n", bundel.getString("keuze"), bundel.getString("afsluiten"), bundel.getString("aanmelden"), bundel.getString("registreren"));
                    keuze = input.nextInt();
                }
                switch (keuze) {
                    case 0:
                        System.out.printf("%n%s.%n", bundel.getString("spelStopt"));
                        herhalen = false;
                        System.exit(1);
                        break;
                    case 1:
                        new ConsoleUC1(domeinController, vertaler).run();
                        herhalen = false;
                        break;
                    case 2:
                        new ConsoleUC2(domeinController, vertaler).run();
                        herhalen = false;
                        break;
                }
            } catch (InputMismatchException ime) {
                System.out.println(ime.getMessage());
            } catch (FouteAanmeldOfRegistreerGegevensException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException iae) {
                System.out.println(iae.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        herhalen = true;

        while (herhalen){
            try {
                do {
                        System.out.printf("%n%s, %s%n", bundel.getString("welkomZin"), domeinController.geefGebruikersnaamAangemeldeSpeler());
                        System.out.println(domeinController.geefGepasteMenuVoorSpeler(vertaler));
                        keuze = input.nextInt();
                    } while (keuze < 0 || keuze > (domeinController.whileKeuze()));

                    switch (keuze) {
                        case 1:
                            new ConsoleUC3(domeinController, vertaler).run();
                            break;
                        case 2:
                            if (domeinController.isAdmin()) {
                                new ConsoleUC5(domeinController, vertaler).run();
                            } else {
                                System.exit(1);
                            }
                            break;
                        case 3:
                            new ConsoleUC7(domeinController, vertaler).run();
                            break;
                        case 4:
                            herhalen = false;
                            break;
                    }

            } catch (InputMismatchException ime){
                input.nextLine();
                System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
            } catch (NumberFormatException nfe){
                System.out.println(nfe.getMessage());
            } catch (IllegalArgumentException iae){
                System.out.println(iae.getMessage());
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
