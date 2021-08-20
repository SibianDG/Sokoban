package ui;

import domein.DomeinController;
import exceptions.FouteZetException;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleUC4 {
    private DomeinController domeinController;
    private Vertaler vertaler;

    public ConsoleUC4(DomeinController domeinController, Vertaler vertaler) {
        this.domeinController = domeinController;
        this.vertaler = vertaler;
    }

    public void run(){
        Scanner input = new Scanner(System.in);
        ResourceBundle bundel = vertaler.getBundle();

        int keuze = -1;
        boolean herhalen = true;
        System.out.println(domeinController.printSpelbord());

        while (herhalen){
            try {
                do {
                    boolean herhalenKeuze = true;
                    while (herhalenKeuze || keuze < 1 || keuze >= 7){
                        try {
                            System.out.printf("%s:", bundel.getString("keuze"));
                            System.out.printf("%n1. %s%n2. %s%n3. %s%n4. %s%n5. %s%n6. %s%n", bundel.getString("boven"), bundel.getString("rechts"), bundel.getString("beneden"), bundel.getString("links"), bundel.getString("stop"), bundel.getString("reset"));
                            keuze = input.nextInt();
                            herhalenKeuze = false;
                        } catch (InputMismatchException ime) {
                            input.nextLine();
                            System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
                        } catch (FouteZetException fze) {
                            System.out.println(fze.getMessage());
                        }

                    }
                    switch (keuze){
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            domeinController.verplaatsSpeler(keuze);
                            break;
                        case 5:
                            herhalen = false;
                            break;
                        case 6:
                            System.out.printf("%s.%n", bundel.getString("resettenSpelbord"));
                            domeinController.resetSpelbord();
                            break;
                        
                    }
                    System.out.printf("%s: %d%n", bundel.getString("aantalStappen") ,domeinController.geefAantalStappenHuidigSpel());
                    System.out.println(domeinController.printSpelbord());
                } while (keuze != 5 && !(domeinController.isHuidigSpelbordVoltooid()));

                herhalen = false; 
            } catch (InputMismatchException ime){
                input.nextLine();
                System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
            } catch (FouteZetException fze){
                System.out.println(bundel.getString(fze.getMessage()));
            } 
        }
        System.out.printf("%s: %d, %s: %d%n", bundel.getString("aantalSpelborden"), domeinController.geefAantalSpelbordenVoorSpel(), bundel.getString("voltooideSpelborden"), domeinController.geefAantalVoltooideSpelbordenVoorSpel());
        if (keuze == 5){
            System.out.printf("%s.%n", bundel.getString("gestopt"));
            domeinController.resetSpelbord();
            domeinController.verlaatSpel();
        } else {
            System.out.printf("%s.%n", bundel.getString("voltooid"));
        }
    }
}
