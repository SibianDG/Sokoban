package domein;

import exceptions.FouteAanmeldOfRegistreerGegevensException;
import persistentie.SpelerMapper;

public class SpelerRepository {
    SpelerMapper spelerMapper;

    public SpelerRepository() {
        spelerMapper = new SpelerMapper();
    }

    public Speler meldAan(String gebruikersnaam, String wachtwoord) {
        Speler speler = spelerMapper.geefSpeler(gebruikersnaam);
        if (speler == null){
            throw new IllegalArgumentException("errorLogin");
        }
        if(speler.getWachtwoord().equals(wachtwoord)) {
            return speler;
        } else {
        	throw new FouteAanmeldOfRegistreerGegevensException("errorLogin");
        }
    }

    public void registreer(Speler speler) {
        controleerGebruikersnaam(speler.getGebruikersnaam());
        spelerMapper.voegToe(speler);
    }

    private void controleerGebruikersnaam(String gebruikersnaam) {
        if(spelerMapper.bestaatSpelerAl(gebruikersnaam)) {
        	throw new FouteAanmeldOfRegistreerGegevensException("errorGebruikersnaam");
        }
    }

    public int geefSpelerID(String gebruikersnaam) {
        return spelerMapper.geefSpelerID(gebruikersnaam);
    }

}

