package ui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;
import org.w3c.dom.ls.LSOutput;

public class ConsoleUC3 {
	private DomeinController domeinController;
	private Vertaler vertaler;

	
	public ConsoleUC3(DomeinController domeinController, Vertaler vertaler) {
		this.domeinController = domeinController;
		this.vertaler = vertaler;
	}
	
	public void run() {
		int intGekozenSpel;
		int keuze;
		boolean herhalen = true;
		Scanner input = new Scanner(System.in);
		ResourceBundle bundel = vertaler.getBundle();

		while (herhalen){
			try {
				System.out.printf("%s:%n", bundel.getString("spelletjes"));
				StringBuilder alleSpelen = new StringBuilder();
				for (String spelString : domeinController.geefAlleSpelen()) { 
					alleSpelen.append(spelString).append(System.lineSeparator());
				}
				System.out.println(alleSpelen.toString());

				do {
					System.out.printf("%s: %n", bundel.getString("kiesSpel"));
					intGekozenSpel = input.nextInt();
				} while (!alleSpelen.toString().contains(intGekozenSpel + ": ") || intGekozenSpel == 0);

				domeinController.stelHuidigSpelIn(intGekozenSpel);


				System.out.printf("%s: %s%n%n", bundel.getString("gekozen"), domeinController.geefNaamHuidigSpel());

				boolean gebruikerNietWilStoppen = true;
				
				while (!domeinController.isSpelGedaan() && gebruikerNietWilStoppen) { 
					try {
						do {
							System.out.printf("1. %s%n2. %s%n", bundel.getString("voltooiVolgend"), bundel.getString("spelVerlaten"));
							keuze = input.nextInt();
						} while (keuze != 1 && keuze != 2);

						switch (keuze) {
							case 1:
								new ConsoleUC4(domeinController, vertaler).run();
								break;
							case 2:
								System.out.printf("%s%n", bundel.getString("opgegevenSpel"));
								gebruikerNietWilStoppen = false;
								domeinController.verlaatSpel();
								break;
							default:
								System.out.printf("%s%n", bundel.getString("fout"));
						}
					} catch (InputMismatchException ime) {
						input.nextLine();
						System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
					} catch (NumberFormatException nfe) {
						System.out.println(bundel.getString(nfe.getMessage()));
					} catch (IllegalArgumentException iae) {
						System.out.println(bundel.getString(iae.getMessage()));
					}

				} 
				herhalen = false;
				if (domeinController.isSpelGedaan()) {
					System.out.println(bundel.getString("gewonnenTxt"));
					System.out.println(bundel.getString("aantalSpelborden1") + domeinController.geefAantalSpelbordenVoorSpel());
					System.out.println(bundel.getString("voltooideSpelborden1") + domeinController.geefAantalVoltooideSpelbordenVoorSpel());
					domeinController.verlaatSpel();
				}
			} catch (InputMismatchException ime){
				input.nextLine();
				System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
			}
		}

	}
}
