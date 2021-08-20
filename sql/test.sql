/*
INSERT INTO Speler(voornaam, familienaam, gebruikersnaam, wachtwoord, isAdmin) VALUES('Admin', 'Admin', 'Admin123', 'Admin123', true) ;
INSERT INTO Spel(naam, spelerID) VALUES('Makkelijk', 1) ;
INSERT INTO Spel(naam, spelerID) VALUES('Moeilijk', 1) ;
INSERT INTO Spel(naam, spelerID) VALUES('Hard', 1) ;

INSERT INTO Spelbord(bord, spelID) VALUES ('###########..     ###..      ##### #   ##      # ##   ##x  ##   x  # ## x x#  ###       M###########', 1);
SELECT * FROM Speler
*/
INSERT INTO Spelbord(bord, spelID) VALUES ('                      ######    # .  #    # x  #    # M  #    #    #    ######                      ', 2);
SELECT * FROM Spelbord WHERE spelID = 2 ORDER BY id ASC;





























/* INSERT INTO Speler(voornaam, familienaam, gebruikersnaam, wachtwoord, isAdmin) VALUES('Admin', 'Admin', 'Admin123', 'Admin123', true) ;
INSERT INTO Spel(naam) VALUES ('Makkelijk');
INSERT INTO Spel(naam) VALUES ('Moeilijk');
INSERT INTO Spel(naam) VALUES ('Hard');

INSERT INTO Spelbord(bord) VALUES ('acxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx');
INSERT INTO Spelbord(bord) VALUES ('fbbxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx');

INSERT INTO Speler_Spel(spelerID, spelID) VALUES (1, 2);
*/
/*
INSERT INTO Spel_Spelbord(spelID, spelbordID) VALUES (2, 1);
INSERT INTO Spel_Spelbord(spelID, spelbordID) VALUES (2, 2);
INSERT INTO Spel_Spelbord(spelID, spelbordID) VALUES (2, 3);
*/

-- INSERT INTO Speler_Spelbord(spelerID, spelbordID, topscore, isVoltooid) VALUES (1, 2, 10, false);

-- SELECT * FROM Spel s JOIN Spel_Spelbord ssb ON s.id = ssb.spelID JOIN Spelbord sb ON ssb.spelbordID = sb.id WHERE spelID = 2;

-- UPDATE Speler u JOIN User_Spel us ON u.id = us.userID JOIN Spel s ON us.spelID = s.id JOIN Spel_Spelbord ssb ON s.id = ssb.spelID SET isVoltooid = false WHERE us.userID <> 1;

-- UPDATE Speler_Spelbord SET topscore = 5 WHERE spelerID = 1 AND spelbordID = 1;
-- SELECT * FROM Speler_Spelbord;

-- SELECT *  FROM Spel s JOIN Spel_Spelbord ssb ON s.spelID = ssb.spelID;

/*
 SELECT * FROM
	Spelbord LEFT JOIN Speler_Spelbord ON Spelbord.id = Speler_Spelbord.spelbordID
    LEFT JOIN Speler ON Speler.id = Speler_Spelbord.spelerID
    LEFT JOIN Speler_Spel ON Speler.id = Speler_Spel.spelerID
    LEFT JOIN Spel ON Speler_Spel.spelID = Spel.id
    LEFT JOIN Spel_Spelbord ON Spelbord.id = Spel_Spelbord.spelbordID
WHERE (Speler.gebruikersnaam = 'Admin123' OR Speler.gebruikersnaam IS NULL) AND Spel_Spelbord.spelID = 2
;
*/

-- SELECT * FROM ID222177_g21.Spelbord LEFT JOIN ID222177_g21.Speler_Spelbord ON Spelbord.id = Speler_Spelbord.spelbordID LEFT JOIN ID222177_g21.Speler ON Speler.id = Speler_Spelbord.spelerID LEFT JOIN ID222177_g21.Spel_Spelbord ON Spelbord.id = Spel_Spelbord.spelbordID LEFT JOIN ID222177_g21.Spel ON Spel_Spelbord.spelID = Spel.id WHERE (Speler.gebruikersnaam = 'Admin123' OR Speler.gebruikersnaam IS NULL) AND Spel_Spelbord.spelID = 2;
    
 --   SELECT * FROM Spelbord;
    
-- SELECT * FROM ID222177_g21.Speler JOIN ID222177_g21.Speler_Spel  ON Speler.id = Speler_Spel.spelerID JOIN ID222177_g21.Spel ON Speler_Spel.spelID = Spel.id JOIN ID222177_g21.Speler_Spelbord ON Speler.id = Speler_Spelbord.SpelerID JOIN ID222177_g21.Spelbord ON Speler_Spelbord.spelbordID = Spelbord.id WHERE Speler.gebruikersnaam = 'Admin123' AND Spel.id = 2;

    
    -- SELECT * FROM Speler_Spelbord;

-- SELECT * FROM Speler_Spelbord WHERE spelerID = 1 AND spelbordID = 1 ;