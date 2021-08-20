package persistentie;

import domein.Spel;
import domein.Spelbord;
import domein.Veld;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpelbordMapper {
    private static final String INSERT_SPELBORD = "INSERT INTO ID222177_g21.Spelbord (bord, spelID)"
            + "VALUES (?, ?) ;";
    private static final String DELETE_SPELBORD = "DELETE FROM ID222177_g21.Spelbord WHERE id = ?;";
    
    public List<Spelbord> geefAlleSpelbordenVoorSpel(int spelID) {
        List<Spelbord> spelbordList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
             PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g21.Spelbord WHERE spelID = ? ORDER BY volgnummer ASC")) {
            query.setInt(1, spelID);

            try (ResultSet rs = query.executeQuery()) {
                while(rs.next()) {
                    int spelbordID = rs.getInt("id");
                    spelbordList.add(new Spelbord(spelbordID));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return spelbordList;
    }
    
    public List<Integer> geefSpelbordIDsVoorSpel(int spelID) {
    	List<Integer> IDs = new ArrayList<>();
    	try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement("SELECT id FROM ID222177_g21.Spelbord WHERE spelID = ?;")) {
    			query.setInt(1, spelID);
    			try(ResultSet rs = query.executeQuery()) {
    				while (rs.next()) {
    					IDs.add(rs.getInt("id"));
    			}             
    				}
         } catch (SQLException ex) {
        	 throw new RuntimeException(ex);
       }
    	return IDs;
    }
    public void verwijderSpelbord(int spelbordID) {
    	 try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
             PreparedStatement query = conn.prepareStatement(DELETE_SPELBORD)) {
             query.setInt(1, spelbordID);
             query.executeUpdate();

         } catch (SQLException ex) {
             throw new RuntimeException(ex);
         }
    }

    public void maakSpelbordAan(Spelbord huidigSpelbord) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("INSERT INTO ID222177_g21.Spelbord(spelID, volgnummer) VALUES (?, ?);")) {
            query.setInt(1, huidigSpelbord.getSpel().getSpelID());
            query.setInt(2, huidigSpelbord.getSpel().getIndexHuidigSpelbord());
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public int geefSpelbordIDMetIndex(Spelbord huidigSpelbord) {
        int id;
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
             PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g21.Spelbord WHERE spelID = ? AND volgnummer = ?;")) {
            query.setInt(1, huidigSpelbord.getSpel().getSpelID());
            query.setInt(2, huidigSpelbord.getSpel().getIndexHuidigSpelbord());
            try(ResultSet rs = query.executeQuery()) {
                rs.beforeFirst();
                rs.next();
                id = rs.getInt("id");

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return id;
    }

    public void voegTekenToeAanSpelbord(List<String[]> resultaat) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ID222177_g21.Veld VALUES (?,?,?,?);")) {
            for (String[] rij : resultaat){
                statement.setInt(1, Integer.parseInt(rij[0]));
                statement.setInt(2, Integer.parseInt(rij[1]));
                statement.setString(3, rij[2]);
                statement.setInt(4, Integer.parseInt(rij[3]));
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Veld[][] stelVeldenIn(int spelbordID) {
        Veld[][] velds = new Veld[10][10];
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement query = conn.prepareStatement("SELECT * FROM ID222177_g21.Veld WHERE spelbordID = ?;")) {
            query.setInt(1, spelbordID);
            try(ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    int x = rs.getInt("x");
                    int y = rs.getInt("y");
                    char teken = rs.getString("teken").charAt(0);
                    velds[x][y] = new Veld(teken);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return velds;
    }

	public void updateSpelbord(Spelbord gewijzigdSpelbord , int positie) {
		Veld gewijzigdVeld = gewijzigdSpelbord.geefVeldOpPositie(positie);
		String tekenGewijzigdVeld = gewijzigdVeld.getType().getTeken().toString();
		
		int x = gewijzigdSpelbord.geefXwaardeVeldOpPositie(positie);
		int y = gewijzigdSpelbord.geefYwaardeVeldOpPositie(positie);
		
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);	
            PreparedStatement query = conn.prepareStatement("UPDATE ID222177_g21.Veld\r\n" + 
                		"SET x = ?, y = ? , teken = ?\r\n" + 
                		"WHERE spelbordID = ? AND x = ? AND y = ?;")) {
            query.setInt(1, x);
            query.setInt(2, y);
            query.setString(3, tekenGewijzigdVeld);
            query.setInt(4, gewijzigdSpelbord.getSpelbordID());
            query.setInt(5, x);
            query.setInt(6, y);
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
	}

	public void voegNieuwVeldToeAanSpelbord(Spelbord gewijzigdSpelbord, int positie) {
		Veld gewijzigdVeld = gewijzigdSpelbord.geefVeldOpPositie(positie);
		String tekenGewijzigdVeld = gewijzigdVeld.getType().getTeken().toString();
		
		int x = gewijzigdSpelbord.geefXwaardeVeldOpPositie(positie);
		int y = gewijzigdSpelbord.geefYwaardeVeldOpPositie(positie);
		
		try(Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ID222177_g21.Veld VALUES (?,?,?,?);")) {
                statement.setInt(1, x);
                statement.setInt(2, y);
                statement.setString(3, tekenGewijzigdVeld);
                statement.setInt(4, gewijzigdSpelbord.getSpelbordID());
                statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
	}
	
	public void verwijderSpelbord(Spelbord gewijzigdSpelbord) {
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement statement = conn.prepareStatement("DELETE FROM ID222177_g21.Veld\r\n" + 
            		"WHERE spelbordID = ?;")) {
                statement.setInt(1, gewijzigdSpelbord.getSpelbordID());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
	}
	
	public void verwijderVeldUitSpelbord(Spelbord gewijzigdSpelbord, int positie) {
		
		try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
            PreparedStatement statement = conn.prepareStatement("DELETE FROM ID222177_g21.Veld\r\n" + 
            		"WHERE spelbordid = ? AND x = ? AND y = ?")) {
                statement.setInt(1, gewijzigdSpelbord.getSpelbordID());
                statement.setInt(2, gewijzigdSpelbord.geefXwaardeVeldOpPositie(positie));
                statement.setInt(3, gewijzigdSpelbord.geefYwaardeVeldOpPositie(positie));
                statement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
	}
}
