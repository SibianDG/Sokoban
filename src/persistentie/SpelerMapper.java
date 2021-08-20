package persistentie;

import domein.Speler;

import java.sql.*;

public class SpelerMapper {
    private static final String INSERT_USER = "INSERT INTO ID222177_g21.Speler (voornaam, familienaam, gebruikersnaam, wachtwoord, isAdmin)"
            + "VALUES (?, ?, ?, ?, ?) ;";

    public void voegToe(Speler speler) {

        try(Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement(INSERT_USER)) {
            query.setString(1, speler.getVoornaam());
            query.setString(2, speler.getFamilienaam());
            query.setString(3, speler.getGebruikersnaam());
            query.setString(4, speler.getWachtwoord());
            query.setBoolean(5, speler.isAdmin());
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean bestaatSpelerAl(String gebruikersnaam) {
        try(Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g21.Speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {

            throw new RuntimeException(ex);
        }
    }

    public int geefSpelerID(String gebruikersnaam) {
        int spelerID = -1;
        try(Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT id FROM ID222177_g21.Speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gebruikersnaam);
            try(ResultSet rs = query.executeQuery()) {
                if(rs.next()) {
                    spelerID = rs.getInt("id");
                    return spelerID;
                }
            }
        } catch (SQLException ex) {

            throw new RuntimeException(ex);
        }
        return spelerID;
    }

    public Speler geefSpeler(String gebruikersnaam) {
        Speler speler = null;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g21.Speler WHERE gebruikersnaam = ?")) {
            query.setString(1, gebruikersnaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    String voornaam = rs.getString("voornaam");
                    String familienaam = rs.getString("familienaam");
                    String wachtwoord = rs.getString("wachtwoord");
                    boolean isAdmin = rs.getBoolean("isAdmin");
                    int spelerID = rs.getInt("id");

                    speler = new Speler(voornaam, familienaam, gebruikersnaam, wachtwoord, isAdmin, spelerID);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return speler;
    }
}
