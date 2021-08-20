package ui;

import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class ConsoleUC8 {
	private DomeinController domeinController;
	private Vertaler vertaler;

	public ConsoleUC8(DomeinController domeinController, Vertaler vertaler) {
		this.domeinController = domeinController;
		this.vertaler = vertaler;
	}

	public void run() {

		Scanner input = new Scanner(System.in);
		ResourceBundle bundel = vertaler.getBundle();
		int keuzeAanpassen, keuzeActie;
		int keuzePositie = 0;
		int keuzeAlternatief = 0;
		boolean aanpassen = true;

		System.out.printf("%s: %s%n", bundel.getString("spelKort"), domeinController.geefNaamHuidigSpel());

		while (aanpassen) {

			System.out.printf("%S:%n%n%s", bundel.getString("huidigSpelbord"), domeinController.geefHuidigSpelbord());

			do {
				System.out.printf("%s?%n1. %s%n2. %s%n", bundel.getString("spelbordAanpassen"), bundel.getString("ja"),
						bundel.getString("nee"));
				keuzeAanpassen = input.nextInt();
			} while (!(keuzeAanpassen == 1 || keuzeAanpassen == 2));

			if (keuzeAanpassen == 2) {
				try {
					domeinController.updateSpelbord();
					System.out.printf("%s!%n", bundel.getString("succesWijziging"));
					aanpassen = false;
				} catch (IllegalArgumentException e) {
					System.out.println(bundel.getString(e.getMessage()) + "!");
					System.out.printf("%s.%n%n", bundel.getString("foutUpdate"));

					do {
						System.out.printf("%s?%n1. %s%n2. %s%n", bundel.getString("keuzeWijzigingen"),
								bundel.getString("aanpassen"), bundel.getString("ongedaanMaken"));
						keuzeAlternatief = input.nextInt();
					} while (!(keuzeAlternatief == 1 || keuzeAlternatief == 2));
					if (keuzeAlternatief == 2) {
						domeinController.maakWijzigingenOngedaan();
						domeinController.resetSpelbord();
						System.out.printf("%s!%n", bundel.getString("ongedaanGemaakt"));
						aanpassen = false;
					} if (keuzeAlternatief == 1){
						System.out.printf("%S:%n%n%s", bundel.getString("huidigSpelbord"), domeinController.geefHuidigSpelbord());
						aanpassen = false;
					}
				}

			}

			if (keuzeAlternatief == 2){
				break;
			}

			if (keuzeAanpassen == 1) {
				do {
					System.out.printf("%s. [1-100] : %n", bundel.getString("kiesPositie"));
					keuzePositie = input.nextInt();
				} while (!(keuzePositie >= 1 && keuzePositie <= 100));

				keuzePositie -= 1;
				do {
					System.out.printf("%s:%n1. %s%n2. %s%n3. %s%n4. %s%n5. %s%n", bundel.getString("kiesActie"),
							bundel.getString("plaatsenMuur"), bundel.getString("plaatsenDoel"),
							bundel.getString("plaatsenMan"), bundel.getString("plaatsenKist"),
							bundel.getString("veldLeeg"));
					keuzeActie = input.nextInt();

				} while (keuzeActie < 1 || keuzeActie > 5);
				domeinController.wijzigSpelbordOpPositie(keuzePositie, keuzeActie);
			}
			
		}

	}

}
