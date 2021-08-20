package persistentie;

import domein.Spel;
import domein.Speler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpelMapper {
    private static final String INSERT_SPEL = "INSERT INTO ID222177_g21.Spel (naam, spelerID)"
            + "VALUES (?, ?) ;";

    public void voegSpelToe(Spel spel) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement(INSERT_SPEL)) {
            query.setString(1, spel.getNaam());
            query.setInt(2, spel.getMakerSpel().getSpelerID());
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Spel> geefAlleSpellen() {
        List<Spel> spelList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
             PreparedStatement query = conn.prepareStatement("SELECT Spel.id AS spelID, naam, spelerID, voornaam, familienaam, gebruikersnaam, isAdmin, wachtwoord FROM ID222177_g21.Spel JOIN ID222177_g21.Speler ON Spel.spelerID = Speler.id ORDER BY Spel.id ASC;");
             ResultSet rs = query.executeQuery()) {

            while (rs.next()) {
                int spelID = rs.getInt("spelID");
                String naam = rs.getString("naam");
                int spelerID = rs.getInt("spelerID");
                String voornaam = rs.getString("voornaam");
                String familienaam = rs.getString("familienaam");
                String gebruikersnaam = rs.getString("gebruikersnaam");
                String wachtwoord = rs.getString("wachtwoord");
                boolean isAdmin = rs.getBoolean("isAdmin");
                spelList.add(new Spel(spelID, naam, new Speler(voornaam, familienaam, gebruikersnaam, wachtwoord, isAdmin, spelerID)));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return spelList;
    }

    public int geefSpelIDNieuwSpel(Spel nieuwSpel) {
        int spelID = -1;
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
             PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g21.Spel WHERE naam = ?")) {
            query.setString(1, nieuwSpel.getNaam());
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    spelID = rs.getInt("id");
                    return spelID;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return spelID;
    }
    
    public List<String> geefSpellenGemaaktDoorSpeler(int spelerID) {
    	List<String> spellenGemaaktDoorSpeler = new ArrayList<>();
    	
    	try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
               PreparedStatement query = conn.prepareStatement("SELECT id, naam FROM ID222177_g21.Spel WHERE spelerid = ?;")) {
               query.setInt(1, spelerID);
               try (ResultSet rs = query.executeQuery()) {
                   while (rs.next()) {
                	   int spelID = rs.getInt("id");
                	   String spelNaam = rs.getString("naam");
                	   StringBuilder spelString = new StringBuilder();
                	   spelString.append(spelID).append(" ").append(spelNaam);
                       spellenGemaaktDoorSpeler.add(spelString.toString());
                   }
               }
           } catch (SQLException ex) {
               throw new RuntimeException(ex);
           }
    	return spellenGemaaktDoorSpeler;
    }

    public void verwijderSpel(Spel huidigSpel) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("DELETE FROM ID222177_g21.Spel WHERE id = ?;")) {
            query.setInt(1, huidigSpel.getSpelID());
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
