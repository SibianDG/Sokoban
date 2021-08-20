package ui;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConsoleUC6 {
	private DomeinController domeinController;
	private Vertaler vertaler;

	public ConsoleUC6(DomeinController domeinController, Vertaler vertaler) {
		this.domeinController = domeinController;
		this.vertaler = vertaler;
	}

	public void run() {
		Scanner input = new Scanner(System.in);
		int keuze = 0, plaats = -1, veldTeken = -1;
		boolean keuzeHerhalen = true;
		ResourceBundle bundel = vertaler.getBundle();

		System.out.printf("%s%n", bundel.getString("spelbordMaken"));
		domeinController.maakLeegSpelbordAan();

		while (keuzeHerhalen || keuze != 2) {
			try {
				System.out.printf("%s?%n1. %s\t2. %s : ", bundel.getString("kiesVeld"), bundel.getString("ja"), bundel.getString("nee"));
				keuze = input.nextInt();
				keuzeHerhalen = false;

				if (keuze == 1) {
					System.out.printf("%S:%n", bundel.getString("spelbord"));
					System.out.println(domeinController.toonNieuwSpelbord());

					keuzeHerhalen = true;
					while (keuzeHerhalen){
						try {
							System.out.printf("%s. [1-100] : ", bundel.getString("kiesPlaats"));
							plaats = input.nextInt();
							if (plaats > 0 && plaats <= 100){
								keuzeHerhalen = false;
							} else {
								System.out.printf("%s.%n", bundel.getString("interval"));
							}
						} catch (InputMismatchException ime){
							input.nextLine();
							System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
						} catch (IllegalArgumentException e){
							System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
						}
					}
					keuzeHerhalen = true;
					while (keuzeHerhalen){
						try {
							System.out.printf("%s:" , bundel.getString("kiesType"));
							System.out.printf("%15s%15s%15s%15s%25s%n", "1. " + bundel.getString("muur"), "2. " + bundel.getString("doel"), "3. " + bundel.getString("mannetje"), "4. " + bundel.getString("kist"), "5. " + bundel.getString("veldLeeg"));
							veldTeken = input.nextInt();
							if (veldTeken >= 1 && veldTeken < 6){
								keuzeHerhalen = false;

							}
						} catch (InputMismatchException ime) {
							input.nextLine();
							System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
						} catch (IllegalArgumentException e){
							System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
						}
					}
					domeinController.voerActieUitOpPositie(plaats - 1, veldTeken);
					System.out.println(domeinController.toonNieuwSpelbord());

				} else if (keuze == 2) {
					System.out.printf("%s %s.%n", domeinController.geefGebruikersnaamAangemeldeSpeler(), bundel.getString("geenVeld"));
				}
			} catch (InputMismatchException ime) {
				input.nextLine();
				System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
			} catch (IllegalArgumentException iae) {
				System.out.printf("%s!%n%n", bundel.getString("keuzegetal"));
			}
		}
		try {
			domeinController.voegNieuwSpelbordToe();
			System.out.printf("%s %s%n", domeinController.geefGebruikersnaamAangemeldeSpeler(), bundel.getString("bevestigingSpelbord"));
		} catch (Exception e ){
			System.out.println(bundel.getString(e.getMessage()));
		}
	}
}
