package ui;

import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import domein.DomeinController;

public class ConsoleUC7 {
	private DomeinController domeinController;
    private Vertaler vertaler;

    public ConsoleUC7(DomeinController domeinController, Vertaler vertaler) {
        this.domeinController = domeinController;
        this.vertaler = vertaler;
    } 
    
    public void run() {
 	
    	Scanner input = new Scanner(System.in);
    	int keuze;
    	String gebruikersnaam = domeinController.geefGebruikersnaamAangemeldeSpeler();
    	int spelerId = domeinController.geefSpelerID(gebruikersnaam);
    	List<String> spellen = domeinController.geefSpellenGemaaktDoorSpeler(spelerId);
    	String alleSpelen = "";
		ResourceBundle bundel = vertaler.getBundle();

    	System.out.printf("%s %s%n", bundel.getString("aangemaakteSpellen"), gebruikersnaam);

    	for(String spel: spellen) {
    		System.out.printf("%s%n", spel);
    		alleSpelen += String.format("%s%n", spel);
    	}

    	do {
    		System.out.printf("%s? ", bundel.getString("wijzigenSpel"));
    		keuze = input.nextInt();
    	}while(!alleSpelen.contains(Integer.toString(keuze)));

    	int spelID = keuze ;
    	boolean herhalen = true;
   	
    	try {
    		domeinController.stelHuidigSpelIn(spelID);
    		System.out.printf("%s: %s%n", bundel.getString("spelnaam"), domeinController.geefNaamHuidigSpel());
    	}
    	catch(NullPointerException e) {
    		System.out.println(bundel.getString(e.getMessage()));
    	} 	
    	catch(Exception e) {
    		System.out.println(bundel.getString("foutmelding"));
    	}
    	
    	while(herhalen)
    	{
    		List<String> spelborden = domeinController.geefSpelbordenVoorSpel();
        	for(String bord: spelborden) {
				System.out.println(bord);
        	}
        	
    		int keuze3;
    		
    		do {
    			System.out.printf("%s?%n1. %s%n2. %s%n", bundel.getString("keuze"), bundel.getString("wijzigSpelbord"), bundel.getString("stop"));
        		keuze3 = input.nextInt();
    		}while(!(keuze3 == 1 || keuze3 == 2));
        	
    		if(keuze3 == 2) {
    			herhalen = false;
    			break;
    		}
        	
    		int keuzeSpelbord;
    		StringBuilder keuzeSpelbordString = new StringBuilder();
        	do {
        		System.out.printf("%s? ", bundel.getString("wijzigenSpelbord"));
            	keuzeSpelbord = input.nextInt();
            	keuzeSpelbordString.append(keuzeSpelbord);
        	}while(spelborden.contains(keuzeSpelbordString.toString()));
        	
        	try {
        		domeinController.stelHuidigSpelbordIn(keuzeSpelbord - 1);
            	System.out.println(domeinController.geefHuidigSpelbord());
        	}catch(IndexOutOfBoundsException e) {
        		System.out.println(bundel.getString("foutmelding"));
        	}
        	catch(Exception e) {
        		e.printStackTrace();
        		System.out.println(bundel.getString("foutmelding"));
        	}
        	
        	int keuzeActie;
        	
        	do {
				System.out.printf("%s?%n1. %s%n2. %s%n", bundel.getString("keuzeSpelbord"), bundel.getString("wijzigen"), bundel.getString("verwijderen"));
				keuzeActie = input.nextInt();
        	} while(!(keuzeActie == 1 || keuzeActie == 2));
        	
        	switch(keuzeActie) {
        		case 1: 
        				new ConsoleUC8(domeinController, vertaler).run();
        				break;
        		case 2: 
        				if(domeinController.geefAantalSpelbordenVoorSpel() >= 2) {

        					try {
        						domeinController.verwijderSpelbord();
        					}catch(Exception e) {
        						System.out.println(bundel.getString("foutVerwijderen"));
        					}
        					
        					System.out.printf("%s %d %s.%n", bundel.getString("spelbord"), keuzeSpelbord, bundel.getString("verwijderd"));
        				}
        				else
        					System.out.printf("%s.", bundel.getString("foutVerwijderenSpelbord"));
        				
        				break;
        	}
    	}    	

    	System.out.printf("%n%s %s %s %d %s.%n", bundel.getString("spel"), domeinController.geefNaamHuidigSpel(), bundel.getString("gewijzigd"), domeinController.geefAantalSpelbordenVoorSpel(), domeinController.geefAantalSpelbordenVoorSpel() == 1 ? bundel.getString("enkelvoud") : bundel.getString("meervoud") );
    	System.out.println();
    }
}
