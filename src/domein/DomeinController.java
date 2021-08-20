package domein;

import ui.Vertaler;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DomeinController
{
    private Speler aangemeldeSpeler;
    private SpelerRepository spelerRepository;
    private Spel huidigSpel;
    private SpelRepository spelRepository;

    public DomeinController() {
        this.spelerRepository = new SpelerRepository();
        this.spelRepository = new SpelRepository();
    }

    public void meldAan(String gebruikersnaam, String wachtwoord) {
        aangemeldeSpeler = spelerRepository.meldAan(gebruikersnaam, wachtwoord);
    }

    public String geefGebruikersnaamAangemeldeSpeler() {
        return aangemeldeSpeler.getGebruikersnaam();
    }

    public String geefGepasteMenuVoorSpeler(Vertaler vertaler) {
        ResourceBundle bundel = vertaler.getBundle();
        if(aangemeldeSpeler.isAdmin()) {
            return String.format("1. %s%n2. %s%n3. %s%n4. %s", bundel.getString("speelSpel"), bundel.getString("maakSpel"), bundel.getString("wijzigSpel"), bundel.getString("afsluiten"));
        } else {
            return String.format("1. %s%n2. %s", bundel.getString("speelSpel"), bundel.getString("afsluiten"));
        }
    }

    public void registreer(String voornaam, String familienaam, String gebruikersnaam, String wachtwoord) {

        Speler nieuweSpeler = new Speler(voornaam, familienaam, gebruikersnaam, wachtwoord);
        spelerRepository.registreer(nieuweSpeler);
        nieuweSpeler.setSpelerID(spelerRepository.geefSpelerID(nieuweSpeler.getGebruikersnaam()));
        this.aangemeldeSpeler = nieuweSpeler;
    }
    
    public void stelHuidigSpelIn(int spelID) {

        this.huidigSpel = spelRepository.geefSpelMetID(spelID);
        huidigSpel.resetIndexHuidigSpelbord();
        huidigSpel.setSpelborden(spelRepository.vulSpelbordenIn(spelID));
    }

    public String printSpelbord() {
        return huidigSpel.getHuidigSpelbord().toString();
    }

    public String[][] geefSpelBordAlsStringArray() {
        return huidigSpel.getHuidigSpelbord().toStringArray();
    }

    public boolean isAdmin() {
        return aangemeldeSpeler.isAdmin();
    }

    public String[] geefAlleSpelen() {
        List<Spel> spelList = spelRepository.getSpelList();
        String[] result = new String[spelList.size()];
        int index = 0;
        for(Spel spel: spelList) {
            result[index++] = String.format("%d: %s", spel.getSpelID(), spel.getNaam());
        }
        return result;
    }
    
    public String[][] geefAlleSpellen() {
    	List<Spel> spelList = spelRepository.getSpelList();
    	String[][] result = new String[spelList.size()][2];
    	int index = 0;
    	for(Spel spel : spelList) {
    		result[index][0] = spel.getNaam();
    		result[index][1] = String.format("%d", spel.getSpelID());
    		index++;
    	}
    	return result;
    }

    public void resetSpelbord() {
        huidigSpel.resetHuidigSpelbord(spelRepository.vulSpelbordenIn(huidigSpel.getSpelID()).get(huidigSpel.getIndexHuidigSpelbord()));
    }

    public String geefNaamHuidigSpel() {
        return huidigSpel.getNaam();
    }

    public boolean isHuidigSpelbordVoltooid() {
       return huidigSpel.isHuidigSpelbordVoltooid();
    }

    public void verplaatsSpeler(int keuzeRichting) {
        huidigSpel.verplaatsSpeler(keuzeRichting);
    }

    public boolean isSpelGedaan() {
        return huidigSpel.isEinde();
    }
    
    public void voegSpelToe(String spelNaam) {
    	this.spelRepository.voegSpelToe(spelNaam, aangemeldeSpeler);
    	huidigSpel = spelRepository.stelNieuwSpelIn();
    }

    public void maakLeegSpelbordAan() {
        huidigSpel.maakLeegSpelbordAan();
    }

    public String geefNieuwSpelNaam() {
        return huidigSpel.getNaam();
    }

    public int geefTotaalAantalSpelbordenNieuwSpel() {
        return huidigSpel.getSpelborden().size();
    }

    public void voerActieUitOpPositie(int plaats, int veldTeken) {
        huidigSpel.voerActieUitOpPositie(plaats, veldTeken);
    }

    public String toonNieuwSpelbord() {
        return huidigSpel.toonNieuwSpelbord();
    }

    public int whileKeuze() {
        if (aangemeldeSpeler.isAdmin()){
            return 4;
        }
        return 2;
    }

    public int geefAantalStappenHuidigSpel() {
        return huidigSpel.geefAantalStappenHuidigSpel();
    }

    public void voegNieuwSpelbordToe() {
        Spelbord huidigSpelbord = huidigSpel.getHuidigSpelbord();
        huidigSpelbord.controleerNieuwToegevoegdSpelbord();
        huidigSpel.getSpelborden().add(huidigSpelbord);
        spelRepository.voegSpelbordToe(huidigSpelbord);
        huidigSpel.verhoogIndexHuidigSpelbord();
    }

    public List<String> geefSpelbordenVoorSpel() {
        List<String> res = new ArrayList<>();
        int teller = 1;
        for(Spelbord spelbord: huidigSpel.getSpelborden()) {
            res.add(String.format("Spelbord %d%n%n%s%n", teller, spelbord.toString()));
            teller++;
        }
        return res;
    }
    
    public List<String> geefSpelbordNamenVoorSpel() {
    	List<String> res = new ArrayList<>();
        int teller = 1;
        for(Spelbord spelbord: huidigSpel.getSpelborden()) {
            res.add(String.format("Spelbord %d", teller));
            teller++;
        }
        return res;
    }
    
    public List<String> geefSpellenGemaaktDoorSpeler(int spelerID) {
    	return spelRepository.geefSpellenGemaaktDoorSpeler(spelerID);
    }
    
    public int geefSpelerID(String gebruikersnaam) {
    	return spelerRepository.geefSpelerID(gebruikersnaam);
    }
    
    public int geefAantalSpelbordenVoorSpel() {
        return huidigSpel.geefAantalSpelbordenVoorSpel();
    }
    
    public void verwijderSpelbord() {
    	Spelbord spelbord = huidigSpel.getHuidigSpelbord();
    	huidigSpel.verwijderSpelbord(spelbord);
    	spelRepository.verwijderSpelbord(spelbord.getSpelbordID());
    }
    
    public void wijzigSpelbordOpPositie(int positie, int actie) {
    	huidigSpel.wijzigSpelbordOpPositie(positie, actie);
    	Spelbord gewijzigdSpelbord = huidigSpel.getHuidigSpelbord();
    }
    
    public void updateSpelbord() {
    	Spelbord gewijzigdSpelbord = huidigSpel.getHuidigSpelbord();
    	gewijzigdSpelbord.controleerNieuwToegevoegdSpelbord();
    	
        for (int positie: gewijzigdSpelbord.getOudeVelden().keySet()) {
            if (huidigSpel.getHuidigSpelbord().getNieuweVelden().get(positie).getType() == Type.LEEG) {
                spelRepository.verwijderVeldUitSpelbord(gewijzigdSpelbord, positie);
            }
        }
    	for (int positie: gewijzigdSpelbord.getNieuweVelden().keySet()) {
    		if (huidigSpel.getHuidigSpelbord().getOudeVelden().get(positie).getType() == Type.LEEG && huidigSpel.getHuidigSpelbord().getNieuweVelden().get(positie).getType() != huidigSpel.getHuidigSpelbord().getOudeVelden().get(positie).getType()) {
    			spelRepository.voegNieuwVeldToeAanSpelbord(gewijzigdSpelbord, positie);
        	} else {
    		spelRepository.updateSpelbord(gewijzigdSpelbord, positie);
        	}
    	}
    	gewijzigdSpelbord.getOudeVelden().clear();
    	gewijzigdSpelbord.getNieuweVelden().clear();
    }
    
    public void stelHuidigSpelbordIn(int gekozenSpelbord) {
    	huidigSpel.stelHuidigSpelbordIn(gekozenSpelbord);
    }
    
    
    public String geefHuidigSpelbord() {
    	return huidigSpel.getHuidigSpelbord().toString();
    }

    public int geefAantalVoltooideSpelbordenVoorSpel() {
        return huidigSpel.geefAantalVoltooideSpelbordenVoorSpel();
    }
    
    public void maakWijzigingenOngedaan() {
    	huidigSpel.maakWijzigingenOngedaan();
    }
    
    public void verwijderSpel() {
    	spelRepository.verwijderSpel(huidigSpel);
    }

    public void verlaatSpel() {
        huidigSpel.resetSpel();
        huidigSpel.setSpelborden(spelRepository.vulSpelbordenIn(huidigSpel.getSpelID()));
    }
}