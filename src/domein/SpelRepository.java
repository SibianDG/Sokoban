package domein;

import persistentie.SpelMapper;
import persistentie.SpelbordMapper;

import java.util.ArrayList;
import java.util.List;

public class SpelRepository {

    private SpelMapper spelMapper;
    private SpelbordMapper spelbordMapper;
    private List<Spel> spelList;

    public SpelRepository() {
        spelMapper = new SpelMapper();
        spelbordMapper = new SpelbordMapper();
        spelList = spelMapper.geefAlleSpellen();
    }

    public List<Spel> getSpelList() {
        return spelList;
    }

    public Spel geefSpelMetID(int id) {
        for(Spel spel: spelList) {
        	if(spel.getSpelID() == id) {
            	return spel;
            }
        }
        return null;
    }
    
    private void checkSpelnaam(String naam) {
        List<Spel> spelLijst = getSpelList();
        spelLijst.forEach(spel -> {
            if(spel.getNaam().toLowerCase().equals(naam.toLowerCase())) {
                throw new IllegalArgumentException("naamBestaatReeds");
            }
        });
        if(naam.matches("\\s+")) {
            throw new IllegalArgumentException("spatiesSpelnaam");
        }
    }
    
    public void voegSpelToe(String spelnaam, Speler aangemeldeSpeler) {
        checkSpelnaam(spelnaam);
        Spel nieuwSpel = new Spel(spelnaam, aangemeldeSpeler);
              
        spelMapper.voegSpelToe(nieuwSpel);
        nieuwSpel.setSpelID(spelMapper.geefSpelIDNieuwSpel(nieuwSpel));
               
        this.spelList.add(nieuwSpel);
    }
    
    public void updateSpelbord(Spelbord gewijzigdSpelbord, int positie) {
        spelbordMapper.updateSpelbord(gewijzigdSpelbord, positie);
    }
    
    public List<String> geefSpellenGemaaktDoorSpeler(int spelerID) {
        return spelMapper.geefSpellenGemaaktDoorSpeler(spelerID);
    }

    public List<Spelbord> vulSpelbordenIn(int spelID) {
        List<Spelbord> spelbordList = spelbordMapper.geefAlleSpelbordenVoorSpel(spelID);
        for(Spelbord spelbord: spelbordList) {
            spelbord.setBord(spelbordMapper.stelVeldenIn(spelbord.getSpelbordID()));
        }
        return spelbordList;
    }

    public void voegSpelbordToe(Spelbord huidigSpelbord) {
        spelbordMapper.maakSpelbordAan(huidigSpelbord);
        huidigSpelbord.setSpelbordID(spelbordMapper.geefSpelbordIDMetIndex(huidigSpelbord));
        List<String[]> resultaat = new ArrayList<>();
        int x = 0;
        for(Veld[] rij: huidigSpelbord.getBord()) {
            int y = 0;
            for(Veld kolom: rij) {
                if(kolom.getType().getTeken() != ' ') {
                    resultaat.add(new String[]{String.valueOf(x), String.valueOf(y), String.valueOf(kolom.getType().getTeken()), String.valueOf(huidigSpelbord.getSpelbordID())});
                }
                y++;
            }
            x++;
        }
        spelbordMapper.voegTekenToeAanSpelbord(resultaat);
    }
    
    public void verwijderSpelbord(int spelbordID) {
    	spelbordMapper.verwijderSpelbord(spelbordID);
    }

	public void voegNieuwVeldToeAanSpelbord(Spelbord gewijzigdSpelbord, int positie) {
		spelbordMapper.voegNieuwVeldToeAanSpelbord(gewijzigdSpelbord, positie);
	}

	public void verwijderVeldUitSpelbord(Spelbord gewijzigdSpelbord, int positie) {
		spelbordMapper.verwijderVeldUitSpelbord(gewijzigdSpelbord, positie);
	}

    public Spel stelNieuwSpelIn() {
        return spelList.get(spelList.size()-1);
    }
    
    public void verwijderSpel(Spel huidigSpel) {
        spelList.remove(huidigSpel);
    	spelMapper.verwijderSpel(huidigSpel);
    }

}
