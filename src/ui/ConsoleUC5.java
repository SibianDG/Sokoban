package ui;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class ConsoleUC5 {
	private DomeinController domeinController;
	private Vertaler vertaler;

	public ConsoleUC5(DomeinController domeinController, Vertaler vertaler) {
		this.domeinController = domeinController;
		this.vertaler = vertaler;
	}

	public void run() {

		Scanner input = new Scanner(System.in);
		int keuze = -1;
		String spelnaam;
		ResourceBundle bundel = vertaler.getBundle();

		boolean keuzeHerhalen = true;
		while (keuzeHerhalen){
			try {
				System.out.printf("%s?%n1. %s %n2. %s%n", bundel.getString("spelMaken"), bundel.getString("ja"), bundel.getString("nee"));
				keuze = input.nextInt();
				if (keuze == 1 || keuze == 2){
					keuzeHerhalen = false;
				}
			} catch (InputMismatchException ime){
				input.nextLine();
				System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
			}
		}
		input.nextLine();


		if(keuze == 1) {
			boolean vlag = true;

			do {
				try {

				System.out.printf("%s?%n", bundel.getString("noemenSpel"));

				spelnaam = input.nextLine();
				domeinController.voegSpelToe(spelnaam);
				System.out.printf("%s %s%n", bundel.getString("bevestigingAanmaak"), domeinController.geefNieuwSpelNaam());

				vlag = false;

				} catch(Exception e) {
					System.out.println(bundel.getString(e.getMessage()));
					input.reset();
				}
			} while(vlag);

			while(keuze != 2) {
				keuzeHerhalen = true;
				while (keuzeHerhalen){
					try {
						System.out.printf("%s?%n1. %s%n2. %s%n", bundel.getString("spelbordToevoegen"), bundel.getString("ja"), bundel.getString("nee"));
						keuze = input.nextInt();
						if (keuze == 1 || keuze == 2){
							keuzeHerhalen = false;
						}
					} catch (InputMismatchException ime){
						input.nextLine();
						System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
					}
				}

				if(keuze == 1) {

					new ConsoleUC6(domeinController, vertaler).run();

				} else if(keuze == 2) {
					System.out.printf("%s%n", bundel.getString("geenSpelbord"));
				}
			}

			if(domeinController.geefTotaalAantalSpelbordenNieuwSpel() >= 1) {
				System.out.printf("%s %s %d %s%n", domeinController.geefNaamHuidigSpel(), bundel.getString("aangemaakt"),domeinController.geefTotaalAantalSpelbordenNieuwSpel(), domeinController.geefTotaalAantalSpelbordenNieuwSpel() == 1 ? bundel.getString("spelbord1"):bundel.getString("spelborden"));
			} else {
				System.out.printf("%s%n", bundel.getString("meldingSpelbord"));

				while(true) {
					keuzeHerhalen = true;
					while (keuzeHerhalen) {
						try {
							System.out.printf("%s?%n1. %s%n2. %s%n", bundel.getString("spelbordToevoegen"), bundel.getString("ja"), bundel.getString("nee"));
							keuze = input.nextInt();
							if (keuze == 1 || keuze == 2){
								keuzeHerhalen = false;
							}
						} catch (InputMismatchException ime){
							input.nextLine();
							System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
						}
					}
					if(keuze == 1) {
						new ConsoleUC6(domeinController, vertaler).run();

					} else if(keuze == 2) {
						System.out.printf("%s%n", bundel.getString("geenSpelbord"));
						break;
					}
				}
				if(domeinController.geefTotaalAantalSpelbordenNieuwSpel() >= 1) {
					System.out.printf("%s %s %d %s%n", domeinController.geefNaamHuidigSpel(), bundel.getString("aangemaakt"),domeinController.geefTotaalAantalSpelbordenNieuwSpel(), domeinController.geefTotaalAantalSpelbordenNieuwSpel() == 1 ? bundel.getString("spelbord1"):bundel.getString("spelborden"));
				} else {
					domeinController.verwijderSpel();
					System.out.printf("%s%n", bundel.getString("spelVerwijderd"));
				}
			}
		} else if(keuze == 2) {
			System.out.println(bundel.getString("geenSpel"));
		}
	}
}
