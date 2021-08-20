package domein;

public class Speler {

    private String voornaam;
    private String familienaam;
    private String gebruikersnaam;
    private String wachtwoord;
    private boolean isAdmin;
    private int spelerID;

    public Speler(String voornaam, String familienaam, String gebruikersnaam, String wachtwoord, boolean isAdmin, int spelerID) {
        setVoornaam(voornaam);
        setFamilienaam(familienaam);
        setGebruikersnaam(gebruikersnaam);
        setWachtwoord(wachtwoord);
        setAdmin(isAdmin);
        setSpelerID(spelerID);
    }

    public Speler(String voornaam, String familienaam, String gebruikersnaam, String wachtwoord) {
        this(voornaam, familienaam, gebruikersnaam, wachtwoord, false, -1);
    }

    public String getVoornaam() {
        return voornaam;
    }

    private void setVoornaam(String voornaam) {
        voornaam = voornaam.trim();
        if(familienaam == null || voornaam.isEmpty() || voornaam.equals("\n")) {
            this.voornaam = null;
        }
        this.voornaam = voornaam;
    }

    public String getFamilienaam() {
        return familienaam;
    }

    private void setFamilienaam(String familienaam) {
        familienaam = familienaam.trim();
        if(familienaam == null || familienaam.isEmpty() || familienaam.equals("\n")) {
            this.familienaam = null;
        }
        this.familienaam = familienaam;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        gebruikersnaam = gebruikersnaam.trim();
        if(gebruikersnaam.length() < 8) {
            throw new IllegalArgumentException("lengteNaamTeKort");
        }
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        if(wachtwoord.length() < 8) {
            throw new IllegalArgumentException("lengteWachtwoordTeKort");
        }
        if(!wachtwoord.matches(".*\\d.*")) {
            throw new IllegalArgumentException("cijferInWachtwoord");
        }
        if(wachtwoord.equals(wachtwoord.toLowerCase()) || wachtwoord.equals(wachtwoord.toUpperCase())) {
            throw new IllegalArgumentException("wachtwoordLetters");
        }
        this.wachtwoord = wachtwoord;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    private void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getSpelerID() {
        return spelerID;
    }

    public final void setSpelerID(int spelerID) {
        this.spelerID = spelerID;
    }
}
