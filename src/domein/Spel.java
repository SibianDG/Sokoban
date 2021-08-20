package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Spel
{
	private String naam;
	private int spelID;
	private Speler makerSpel;

	private List<Spelbord> spelborden;
	private Spelbord huidigSpelbord;

	private int indexHuidigSpelbord = 0;

	public Spel(String naam, Speler makerSpel) {
		this(-1, naam, makerSpel);
		spelborden = new ArrayList<>();
	}
	
	public Spel(int spelID, String naam, Speler makerSpel) {
		setSpelID(spelID);
		setNaam(naam);
		setMakerSpel(makerSpel);
	}
	public boolean isEinde() {
		if(spelborden.size() == 1 && huidigSpelbord.isVoltooid()) {
			return true;
		}
		return indexHuidigSpelbord == spelborden.size() && huidigSpelbord.isVoltooid();
	}
	
	private void selecteerVolgendSpelbord() {
		if(huidigSpelbord == null) {
			huidigSpelbord = spelborden.get(0);
		} else if(spelborden.size() > indexHuidigSpelbord+1) {
			huidigSpelbord = spelborden.get(++indexHuidigSpelbord);
		} else if (spelborden.size()-1 == indexHuidigSpelbord){
			indexHuidigSpelbord++;
		}
	}

	public void resetHuidigSpelbord(Spelbord geresetSpelbord) {
		this.huidigSpelbord = geresetSpelbord;
	}
	
	public void verplaatsSpeler(int keuzeRichting) {
		huidigSpelbord.verplaatsSpeler(keuzeRichting);
	}
	
	private void setNaam(String naam) {
		if(naam == null || naam.isEmpty()) {
			throw new IllegalArgumentException("spelnaamLeeg");
		}
		if(naam.contains(" ")) {
			throw new IllegalArgumentException("spatiesSpelnaam");
		}
		
		this.naam = naam;
	}
	
	public String getNaam() {
		return naam;
	}

	public int getSpelID() {
		return spelID;
	}

	public void setSpelID(int spelID) {
		this.spelID = spelID;
	}

	public Spelbord getHuidigSpelbord() {
		return huidigSpelbord;
	}

	public void maakLeegSpelbordAan() {
		huidigSpelbord = new Spelbord(this);
	}

	public void voerActieUitOpPositie(int plaats, int veldTeken) {
		huidigSpelbord.voerActieUitOpPositie(plaats, veldTeken);
	}

	public String toonNieuwSpelbord() {
		return huidigSpelbord.toString();
	}

	public boolean isHuidigSpelbordVoltooid() {
		boolean voltooid = huidigSpelbord.isVoltooid();
		if(voltooid) {
			selecteerVolgendSpelbord();
		}
		return voltooid;
	}

	public Speler getMakerSpel() {
		return makerSpel;
	}

	public void setMakerSpel(Speler makerSpel) {
		this.makerSpel = makerSpel;
	}

	public int geefAantalStappenHuidigSpel() {
		return huidigSpelbord.getAantalStappen();
	}
	
	public int geefAantalSpelbordenVoorSpel() {
		return spelborden.size();
	}
	
	public void verwijderSpelbord(Spelbord huidigSpelbord) {
		spelborden.remove(huidigSpelbord);
		indexHuidigSpelbord = 0;
	}

	public List<Spelbord> getSpelborden() {
		return spelborden;
	}

	public int getIndexHuidigSpelbord() {
		return indexHuidigSpelbord;
	}
	
	public void setSpelborden(List<Spelbord> spelborden) {
		this.spelborden = spelborden;
		huidigSpelbord = spelborden.get(indexHuidigSpelbord);
	}

	public void verhoogIndexHuidigSpelbord() {
		this.indexHuidigSpelbord++;
	}
	
	public void stelHuidigSpelbordIn(int gekozenSpelbord) {
		indexHuidigSpelbord = gekozenSpelbord;
		Spelbord geselecteerdeSpelbord = spelborden.get(gekozenSpelbord);
		setHuidigSpelbord(geselecteerdeSpelbord);

	}
	
	private void setHuidigSpelbord(Spelbord spelbord) {
		this.huidigSpelbord = spelbord;
	}

	public void wijzigSpelbordOpPositie(int positie, int actie) {
		huidigSpelbord.wijzigSpelbordOpPositie(positie, actie);
	}

	public int geefAantalVoltooideSpelbordenVoorSpel() {
		return indexHuidigSpelbord;
	}
	
	public void maakWijzigingenOngedaan() {
		Map<Integer, Veld> oudeVelden = huidigSpelbord.getOudeVelden();
		Set<Integer> oudeVeldenKeys = oudeVelden.keySet();
		int actie = 0;
		for(Integer key: oudeVeldenKeys) {
			switch(oudeVelden.get(key).getType().toString()) {
				case "MUUR": 
					actie = 1;
					break;
				case "DOEL":
					actie = 2;
					break;
				case "MAN":
					actie = 3;
					break;
				case "KIST":
					actie = 4;
					break;
				case "LEEG":
					actie = 5;
					break;
			}	
			wijzigSpelbordOpPositie(key, actie);
		}
	}

	public void resetSpel() {
		indexHuidigSpelbord = 0;
		huidigSpelbord = null;
	}

	public void resetIndexHuidigSpelbord() {
		indexHuidigSpelbord = 0;
	}
}
